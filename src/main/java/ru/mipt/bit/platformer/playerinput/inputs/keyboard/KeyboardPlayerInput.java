package ru.mipt.bit.platformer.playerinput.inputs.keyboard;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.playerinput.actions.EmptyAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInput;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInputActions;

import java.util.Map;

public class KeyboardPlayerInput implements PlayerInput {
    PlayerInputActions inputActions;

    public KeyboardPlayerInput(PlayerInputActions actions) {
        this.inputActions = actions;
    }

    public AbstractAction getAction() {
        for (Map.Entry<Integer, AbstractAction> entry : inputActions.getKeyActions().entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return new EmptyAction();
    }
}
