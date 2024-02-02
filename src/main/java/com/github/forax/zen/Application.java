package com.github.forax.zen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Starts the GUI application with {@link #run(Color, Consumer)}.
 *
 * This is the entrypoint of Zen 6.
 */
public final class Application {
  private Application() {
    throw new AssertionError(); // no instance
  }

  /**
   * Creates a drawing area in fullscreen mode and then runs the application code.
   *
   * @param backgroundColor background color of the drawing area.
   * @param applicationCode code of the application.
   * @throws IllegalStateException is this method is not called by the main thread
   */
  public static void run(Color backgroundColor, Consumer<ApplicationContext> applicationCode) {
    Objects.requireNonNull(backgroundColor);
    Objects.requireNonNull(applicationCode);
    if (EventQueue.isDispatchThread()) {
      throw new IllegalStateException("This code should be executed by the main thread");
    }
    var frame = new Frame();
    frame.setUndecorated(true);
    frame.setBackground(backgroundColor);
    var eventQueue = new ArrayBlockingQueue<Event>(1024);

    class MouseManager extends MouseAdapter implements MouseListener {
      private void generatePointerEvent(PointerEvent.Action action, MouseEvent e) {
        eventQueue.offer(new PointerEvent(action, new PointerEvent.Location(e.getX(), e.getY()), e.getModifiersEx() & EventModifier.MASK));
      }

      @Override
      public void mousePressed(MouseEvent e) {
        generatePointerEvent(PointerEvent.Action.POINTER_DOWN, e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        generatePointerEvent(PointerEvent.Action.POINTER_UP, e);
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        generatePointerEvent(PointerEvent.Action.POINTER_MOVE, e);
      }
    }

    var mouseManager = new MouseManager();
    frame.addMouseListener(mouseManager);
    frame.addMouseMotionListener(mouseManager);
    frame.setFocusable(true);
    frame.addKeyListener(new KeyAdapter() {
      private void generateKeyEvent(KeyboardEvent.Action action, KeyEvent e) {
        eventQueue.offer(new KeyboardEvent(action, KeyboardEvent.Key.key(e.getKeyCode()), e.getModifiersEx() & EventModifier.MASK));
      }

      @Override
      public void keyPressed(KeyEvent e) {
        this.generateKeyEvent(KeyboardEvent.Action.KEY_PRESSED, e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        this.generateKeyEvent(KeyboardEvent.Action.KEY_RELEASED, e);
      }
    });
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
    var applicationThread = Thread.currentThread();
    var context = new ApplicationContext() {
      private volatile Image image;

      void checkThread() {
        if (Thread.currentThread() != applicationThread) {
          throw new IllegalStateException("Only the application thread can interact with the application context");
        }
      }

      @Override
      public void dispose() {
        checkThread();
        frame.dispose();
      }

      @Override
      public ScreenInfo getScreenInfo() {
        checkThread();
        return new ScreenInfo(frame.getWidth(), frame.getHeight());
      }

      @Override
      public Event pollEvent() {
        checkThread();
        return eventQueue.poll();
      }

      @Override
      public Event pollOrWaitEvent(long timeout) {
        checkThread();
        try {
          return eventQueue.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return null;
        }
      }

      @Override
      public void renderFrame(Consumer<Graphics2D> consumer) {
        Objects.requireNonNull(consumer);
        checkThread();
        renderOneFrame(consumer);
      }

      private void renderOneFrame(Consumer<Graphics2D> consumer) {
        Image image;
        if (this.image == null) {
          image = frame.createImage(frame.getWidth(), frame.getHeight());
          image.setAccelerationPriority(1.0F);
          this.image = image;
        } else {
          image = this.image;
        }
        var bufferGraphics = (Graphics2D) image.getGraphics();
        try {
          consumer.accept(bufferGraphics);
        } finally {
          bufferGraphics.dispose();
        }
        try {
          EventQueue.invokeAndWait(() -> {
            var frameGraphics = frame.getGraphics();
            try {
              frameGraphics.drawImage(image, 0, 0, null);
            } finally {
              frameGraphics.dispose();
            }
            Toolkit.getDefaultToolkit().sync();
          });
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } catch (InvocationTargetException e) {
          var cause = e.getCause();
          if (cause instanceof Error error) {
            throw error;
          }
          throw new AssertionError(cause);
        }
      }
    };

    class Waiter implements Runnable {
      private boolean opened;
      private final Object lock = new Object();

      @Override
      public void run() {
        if (frame.getWidth() <= 1) {
          EventQueue.invokeLater(this);
          return;
        }
        frame.setResizable(false);
        synchronized (this.lock) {
          this.opened = true;
          this.lock.notify();
        }
      }

      void await() throws InterruptedException {
        synchronized (this.lock) {
          while (!this.opened) {
            this.lock.wait();
          }
        }
      }
    }

    var waiter = new Waiter();
    EventQueue.invokeLater(waiter);
    try {
      waiter.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return;
    }
    applicationCode.accept(context);
  }
}