package com.github.forax.zen;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
  @Test
  public void applicationTest() throws AWTException {
    Robot robot = new Robot();
    robot.setAutoWaitForIdle(true);

    Application.run(Color.RED, context -> {
      try {
        var screeInfo = context.getScreenInfo();
        assertTrue(screeInfo.width() != 0);
        assertTrue(screeInfo.height() != 0);

        var color = robot.getPixelColor(100, 100);
        assertEquals(Color.RED, color);

      } finally {
        context.dispose();
      }
    });
  }


}