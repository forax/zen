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
    /** The META key. */
    META(KeyEvent.VK_META),
    /** The CONTROL key. */
    CTRL(KeyEvent.VK_CONTROL),
    /** The ALT key. */
    ALT(KeyEvent.VK_ALT),
    /** The SHIFT key. */
    SHIFT(KeyEvent.VK_SHIFT),
    /** The ALT_GR key. */
    ALT_GR(KeyEvent.VK_ALT_GRAPH),

    /** The ESCAPE key. */
    ESCAPE(KeyEvent.VK_ESCAPE),

    /** The UP key. */
    UP(KeyEvent.VK_UP),
    /** The DOWN key. */
    DOWN(KeyEvent.VK_DOWN),
    /** The LEFT key. */
    LEFT(KeyEvent.VK_LEFT),
    /** The RIGHT key. */
    RIGHT(KeyEvent.VK_RIGHT),
    /** The SPACE key. */
    SPACE(KeyEvent.VK_SPACE),

    /** The A key. */
    A(KeyEvent.VK_A),
    /** The B key. */
    B(KeyEvent.VK_B),
    /** The C key. */
    C(KeyEvent.VK_C),
    /** The D key. */
    D(KeyEvent.VK_D),
    /** The E key. */
    E(KeyEvent.VK_E),
    /** The F key. */
    F(KeyEvent.VK_F),
    /** The G key. */
    G(KeyEvent.VK_G),
    /** The H key. */
    H(KeyEvent.VK_H),
    /** The I key. */
    I(KeyEvent.VK_I),
    /** The J key. */
    J(KeyEvent.VK_J),
    /** The K key. */
    K(KeyEvent.VK_K),
    /** The L key. */
    L(KeyEvent.VK_L),
    /** The M key. */
    M(KeyEvent.VK_M),
    /** The N key. */
    N(KeyEvent.VK_N),
    /** The O key. */
    O(KeyEvent.VK_O),
    /** The P key. */
    P(KeyEvent.VK_P),
    /** The Q key. */
    Q(KeyEvent.VK_Q),
    /** The R key. */
    R(KeyEvent.VK_R),
    /** The S key. */
    S(KeyEvent.VK_S),
    /** The T key. */
    T(KeyEvent.VK_T),
    /** The U key. */
    U(KeyEvent.VK_U),
    /** The V key. */
    V(KeyEvent.VK_V),
    /** The W key. */
    W(KeyEvent.VK_W),
    /** The X key. */
    X(KeyEvent.VK_X),
    /** The Y key. */
    Y(KeyEvent.VK_Y),
    /** The Z key. */
    Z(KeyEvent.VK_Z),

    /** An undefined key. */
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
