package ru.mipt.bit.platformer.playerinput;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.playerinput.actions.KeyActions;
import ru.mipt.bit.platformer.playerinput.actions.Direction;

import java.util.Map;

public class PlayerInput {
    public static Direction chooseDirection() {
        Map<Integer, Direction> actions = KeyActions.getKeyActions();
        for (Map.Entry<Integer, Direction> entry : actions.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return Direction.NULL;
    }
}
