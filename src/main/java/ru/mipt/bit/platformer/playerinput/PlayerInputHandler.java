package ru.mipt.bit.platformer.playerinput;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public class PlayerInputHandler {
    public static void handle(Level level, AbstractAction action) {
        action.apply(level);
    }
}
