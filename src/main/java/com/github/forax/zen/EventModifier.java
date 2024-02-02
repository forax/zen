package com.github.forax.zen;

import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 *   An event modifier used by both {@link KeyboardEvent} and {@link PointerEvent}.
 */
public enum EventModifier {
  /** The META modifier. */
  META(InputEvent.META_DOWN_MASK),
  /** The CTRL modifier. */
  CTRL(InputEvent.CTRL_DOWN_MASK),
  /** The ALT modifier. */
  ALT(InputEvent.ALT_DOWN_MASK),
  /** The SHIFT modifier. */
  SHIFT(InputEvent.SHIFT_DOWN_MASK),
  /** The ALT_GR modifier. */
  ALT_GR(InputEvent.ALT_GRAPH_DOWN_MASK);

  private final int modifier;

  EventModifier(int modifier) {
    this.modifier = modifier;
  }

  private static final EventModifier[] MODIFIERS = values();
  static final int MASK = Arrays.stream(MODIFIERS)
      .mapToInt(m -> m.modifier)
      .reduce(0, (a, b) -> a | b);

  static Set<EventModifier> modifierSet(int intModifiers) {
    if (intModifiers == 0) {
      return Set.of();
    }
    var set = EnumSet.noneOf(EventModifier.class);
    for (var modifier : MODIFIERS) {
      if ((intModifiers & modifier.modifier) != 0) {
        set.add(modifier);
      }
    }
    return set;
  }
}