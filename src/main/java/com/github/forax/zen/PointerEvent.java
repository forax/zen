package com.github.forax.zen;

import java.util.Set;

/**
 * A mouse event.
 *
 * @see ApplicationContext#pollEvent()
 */
public final class PointerEvent implements Event {
  /**
   * Action of the event.
   */
  public enum Action {
    /** screen pointer is down */
    POINTER_DOWN,
    /** screen pointer is up */
    POINTER_UP,
    /** screen pointer is moving */
    POINTER_MOVE
  }

  /**
   * Coordinates of the mouse pointer.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   */
  public record Location(int x, int y) {}

  private final Action action;
  private final Location location;
  private final int modifiers;

  private /*lazy*/ Set<EventModifier> modifierSet;

  PointerEvent(Action action, Location location, int modifiers) {
    this.action = action;
    this.location = location;
    this.modifiers = modifiers;
  }

  /**
   * Returns this event action.
   * @return this event action.
   */
  public Action action() {
    return action;
  }

  /**
   * Returns this event location.
   * @return this event location.
   */
  public Location location() {
    return location;
  }

  /**
   * Return this event modifiers.
   * @return this event modifiers.
   */
  public Set<EventModifier> modifiers() {
    if (modifierSet != null) {
      return modifierSet;
    }
    return modifierSet = EventModifier.modifierSet(modifiers);
  }

  @Override
  public String toString() {
    return action + " " + modifiers() + " (" + location  + ')';
  }
}
