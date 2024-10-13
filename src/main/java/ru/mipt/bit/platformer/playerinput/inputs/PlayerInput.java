package ru.mipt.bit.platformer.playerinput.inputs;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public interface PlayerInput {
    AbstractAction getAction();
}
