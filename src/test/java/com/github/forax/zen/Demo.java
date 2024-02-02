package com.github.forax.zen;

import java.awt.Color;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class Demo {
  record Line(int x1, int y1, int x2, int y2, Color color) {}

  private static Line nextLine(RandomGenerator random, int width, int height) {
    int x1 = random.nextInt(width);
    int y1 = random.nextInt(height);
    int x2 = random.nextInt(width);
    int y2 = random.nextInt(height);
    return new Line(x1, y1, x2, y2, nextColor(random));
  }

  private static Color nextColor(RandomGenerator random) {
    int red = random.nextInt(255);
    int green = random.nextInt(255);
    int blue = random.nextInt(255);
    return new Color(red, green, blue);
  }

  public static void main(String[] args) {
    RandomGenerator random = RandomGenerator.getDefault();

    // start the application, create a drawing area, full screen
    Application.run(Color.BLACK, context -> {

      // get the screen info
      var screenInfo = context.getScreenInfo();
      var width = screenInfo.width();
      var height = screenInfo.height();

      for(;;) {
        // get the current event or wait 100 milliseconds
        Event event = context.pollOrWaitEvent(100);

        if (event != null) {
          // debug, print the event
          System.out.println(event);

          switch (event) {
            case PointerEvent pointerEvent -> {

              // if the mouse pointer is up
              if (pointerEvent.action() == PointerEvent.Action.POINTER_UP) {

                // close the screen area
                context.dispose();
                return;
              }
            }
            case KeyboardEvent keyboardEvent -> {

              // if the key is "escape"
              if (keyboardEvent.key() == KeyboardEvent.Key.ESCAPE) {

                // close the screen area
                context.dispose();
                return;
              }
            }
          }
        }

        // create 100 lines
        List<Line> lines = IntStream.range(0, 100).mapToObj(i -> nextLine(random, width, height)).toList();

        // execute all the graphics instructions on a buffer using a Graphics2D object,
        // then draw the buffer on the screen area
        context.renderFrame(graphics2D -> {

          // clear the buffer
          graphics2D.clearRect(0, 0, width, height);

          // draws the lines on the buffer
          for(Line line : lines) {
            graphics2D.setColor(line.color);
            graphics2D.drawLine(line.x1, line.y1, line.x2, line.y2);
          }

          // implicitly, the buffer is drawn to the screen here
        });
      }
    });
  }
}
