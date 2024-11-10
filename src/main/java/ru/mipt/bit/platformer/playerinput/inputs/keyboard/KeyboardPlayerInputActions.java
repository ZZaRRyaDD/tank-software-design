package ru.mipt.bit.platformer.playerinput.inputs.keyboard;

import ru.mipt.bit.platformer.playerinput.actions.EmptyAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInputActions;

import java.util.HashMap;
import java.util.Map;

public class KeyboardPlayerInputActions implements PlayerInputActions {
    private final Map<Integer, AbstractAction> associationKeys = new HashMap<>();

    @Override
    public void registerAction(Integer actionKey, AbstractAction action) {
        associationKeys.put(actionKey, action);
    }

    @Override
    public AbstractAction getKeyAction(Integer actionKey) {
        return associationKeys.getOrDefault(actionKey, new EmptyAction());
    }

    @Override
    public Map<Integer, AbstractAction> getKeyActions() {
        return associationKeys;
    }
}
