package ru.mipt.bit.platformer.playerinput.inputs;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

import java.util.Map;

public interface PlayerInputActions {
    void registerAction(Integer actionKey, AbstractAction action);
    AbstractAction getKeyAction(Integer actionKey);
    Map<Integer, AbstractAction> getKeyActions();
}
