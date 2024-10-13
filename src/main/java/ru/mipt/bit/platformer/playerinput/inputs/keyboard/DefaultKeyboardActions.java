package ru.mipt.bit.platformer.playerinput.inputs.keyboard;

import ru.mipt.bit.platformer.playerinput.actions.MoveAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInputDefaultActions;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultKeyboardActions implements PlayerInputDefaultActions {
    private final Map<Integer, AbstractAction> associationKeys = new HashMap<>();

    public DefaultKeyboardActions() {
        setMoves();
    }
    private void setMoves() {
        associationKeys.put(RIGHT, new MoveAction(Direction.RIGHT));
        associationKeys.put(D, new MoveAction(Direction.RIGHT));
        associationKeys.put(LEFT, new MoveAction(Direction.LEFT));
        associationKeys.put(A, new MoveAction(Direction.LEFT));
        associationKeys.put(UP, new MoveAction(Direction.UP));
        associationKeys.put(W, new MoveAction(Direction.UP));
        associationKeys.put(DOWN, new MoveAction(Direction.DOWN));
        associationKeys.put(S, new MoveAction(Direction.DOWN));
    }

    @Override
    public void registerActions(PlayerInputActions playerInput) {
        for (Map.Entry<Integer, AbstractAction> entry : associationKeys.entrySet()) {
            playerInput.registerAction(entry.getKey(), entry.getValue());
        }
    }
}
