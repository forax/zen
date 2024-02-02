package com.github.forax.zen;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Set;

/**
 * A keyboard event.
 *
 * @see ApplicationContext#pollEvent()
 */
public final class KeyboardEvent implements Event {
  /**
   * Action of the event.
   */
  public enum Action {
    /** key is pressed */
    KEY_PRESSED,
    /** key is released */
    KEY_RELEASED
  }

  /**
   * key of the keyboard
   */
  public enum Key {
    META(KeyEvent.VK_META),
    CTRL(KeyEvent.VK_CONTROL),
    ALT(KeyEvent.VK_ALT),
    SHIFT(KeyEvent.VK_SHIFT),
    ALT_GR(KeyEvent.VK_ALT_GRAPH),

    ESCAPE(KeyEvent.VK_ESCAPE),

    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT),
    SPACE(KeyEvent.VK_SPACE),

    A(KeyEvent.VK_A),
    B(KeyEvent.VK_B),
    C(KeyEvent.VK_C),
    D(KeyEvent.VK_D),
    E(KeyEvent.VK_E),
    F(KeyEvent.VK_F),
    G(KeyEvent.VK_G),
    H(KeyEvent.VK_H),
    I(KeyEvent.VK_I),
    J(KeyEvent.VK_J),
    K(KeyEvent.VK_K),
    L(KeyEvent.VK_L),
    M(KeyEvent.VK_M),
    N(KeyEvent.VK_N),
    O(KeyEvent.VK_O),
    P(KeyEvent.VK_P),
    Q(KeyEvent.VK_Q),
    R(KeyEvent.VK_R),
    S(KeyEvent.VK_S),
    T(KeyEvent.VK_T),
    U(KeyEvent.VK_U),
    V(KeyEvent.VK_V),
    W(KeyEvent.VK_W),
    X(KeyEvent.VK_X),
    Y(KeyEvent.VK_Y),
    Z(KeyEvent.VK_Z),

    UNDEFINED(KeyEvent.VK_UNDEFINED)
    ;

    private final int key;

    Key(int key) {
      this.key = key;
    }

    static Key key(int key) {
      if (key >= KEYS.length) {
        return UNDEFINED;
      }
      return KEYS[key];
    }

    private static final Key[] KEYS;
    static {
      var values = Key.values();
      var max = Arrays.stream(values).mapToInt(k -> k.key).max().orElseThrow();
      var array = new Key[max + 1];
      Arrays.fill(array, UNDEFINED);
      for(var key: values) {
        array[key.key] = key;
      }
      KEYS = array;
    }
  }

  KeyboardEvent(Action action, Key key, int modifiers) {
    this.action = action;
    this.key = key;
    this.modifiers = modifiers;
  }

  private final Action action;
  private final int modifiers;
  private final Key key;

  private /*lazy*/ Set<EventModifier> modifierSet;

  /**
   * Returns this event action.
   * @return this event action.
   */
  public Action action() {
    return action;
  }

  /**
   * Returns this event key.
   * @return this event key.
   */
  public Key key() {
    return key;
  }

  /**
   * Returns this event modifiers.
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
    return action + " " + modifiers() + " (" + key  + ')';
  }
}
