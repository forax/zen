package com.github.forax.zen;

/**
 * Screen size (width x height).
 */
public record ScreenInfo(
    /** width of the application screen. */
    int width,
    /** height of the application screen */
    int height
) {
  @Override
  public String toString() {
    return "ScreenInfo (" + width + " x " + height + ")";
  }
}
