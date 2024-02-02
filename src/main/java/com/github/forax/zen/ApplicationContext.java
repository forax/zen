package com.github.forax.zen;

import java.awt.Graphics2D;
import java.util.function.Consumer;

/**
 * The application context
 * <ul>
 *   <li>{@link #renderFrame(Consumer) renders} graphics instructions ({@link Graphics2D})
 *       to the drawing area of the application,
 *   <li>{@link #pollOrWaitEvent(long) waits} or {@link #pollEvent() asks} for a keyboard event or a pointer event.
 * </ul>
 *
 * This object is created by the {@link Application}.
 */
public interface ApplicationContext {
  /**
   * Returns screen information.
   * @return screen information.
   * @throws IllegalStateException if this method is not called by the application thread.
   */
  ScreenInfo getScreenInfo();
  
  /**
   * Close the drawing area and dispose all the resources.
   * @throws IllegalStateException if this method is not called by the application thread.
   */
  void dispose();
  
  /** 
   * Returns the first event since either the application was
   * started or {@link #pollEvent()}/{@link #pollOrWaitEvent(long)}
   * was called.
   * <p>
   * This method returns immediately.
   *  
   * @return an event or null if no event happens.
   * @throws IllegalStateException if this method is not called by the application thread.
   */
  Event pollEvent();
  
  /** 
   * Returns the first event since either the application was
   * started or {@link #pollEvent()}/{@link #pollOrWaitEvent(long)} was called.
   * This method waits until an event occurs.
   *  
   * @param timeout maximum time to wait (in milliseconds).
   * @return an event or null if the timeout is elapsed.
   * @throws IllegalStateException if this method is not called by the application thread.
   */
  Event pollOrWaitEvent(long timeout);
  
  /**
   * Render a whole frame of the game in one call into a buffer that is then copied to the screen.
   * <p>
   * This method tries to use the hardware acceleration, if possible.
   * 
   * @param consumer a consumer that will be called to render the current frame.
   * @throws IllegalStateException if this method is not called by the application thread.                
   */
  void renderFrame(Consumer<Graphics2D> consumer);
}