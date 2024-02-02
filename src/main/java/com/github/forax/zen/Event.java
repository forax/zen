package com.github.forax.zen;

/**
 * An event is either a pointer event or a keyboard event
 *
 * @see KeyboardEvent
 * @see PointerEvent
 */
public sealed interface Event permits KeyboardEvent, PointerEvent {
}
