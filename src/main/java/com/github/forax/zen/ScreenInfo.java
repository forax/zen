package com.github.forax.zen;

/**
 * Screen size (width x height).
 *
 * @param width width of the application screen.
 * @param height height of the application screen.
 */
public record ScreenInfo(int width, int height) {
  @Override
  public String toString() {
    return "ScreenInfo (" + width + " x " + height + ")";
  }
}
