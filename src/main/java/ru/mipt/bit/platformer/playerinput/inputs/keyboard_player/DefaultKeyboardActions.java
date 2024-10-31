package ru.mipt.bit.platformer.playerinput.inputs.keyboard_player;

import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.draw.decorators.base.Toggle;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.actions.factories.MoveActionFactory;
import ru.mipt.bit.platformer.playerinput.actions.factories.ToggleActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;
import ru.mipt.bit.platformer.playerinput.inputs.DefaultInputActions;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultKeyboardActions implements DefaultInputActions {
    private final Map<Integer, AbstractActionFactory> associationKeys = new HashMap<>();

    public DefaultKeyboardActions(Level level, LevelGraphic levelGraphic) {
        setMoves(level);
        setDrawActions(levelGraphic);
    }

    private void setMoves(Level level) {
        associationKeys.put(RIGHT, new MoveActionFactory(Direction.RIGHT, level));
        associationKeys.put(D, new MoveActionFactory(Direction.RIGHT, level));
        associationKeys.put(LEFT, new MoveActionFactory(Direction.LEFT, level));
        associationKeys.put(A, new MoveActionFactory(Direction.LEFT, level));
        associationKeys.put(UP, new MoveActionFactory(Direction.UP, level));
        associationKeys.put(W, new MoveActionFactory(Direction.UP, level));
        associationKeys.put(DOWN, new MoveActionFactory(Direction.DOWN, level));
        associationKeys.put(S, new MoveActionFactory(Direction.DOWN, level));
    }

    private void setDrawActions(LevelGraphic levelGraphic) {
        associationKeys.put(L, new ToggleActionFactory(levelGraphic));
    }

    @Override
    public void registerActions(InputActions playerInput) {
        for (Map.Entry<Integer, AbstractActionFactory> entry : associationKeys.entrySet()) {
            playerInput.registerAction(entry.getKey(), entry.getValue());
        }
    }
}
