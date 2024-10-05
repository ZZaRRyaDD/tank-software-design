package ru.mipt.bit.platformer.playerinput.actions;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyActions {
    private static final Map<Integer, Direction> actions = new HashMap<>();

    private static void setMoveActions() {
        actions.put(RIGHT, Direction.RIGHT);
        actions.put(D, Direction.RIGHT);
        actions.put(LEFT, Direction.LEFT);
        actions.put(A, Direction.LEFT);
        actions.put(UP, Direction.UP);
        actions.put(W, Direction.UP);
        actions.put(DOWN, Direction.DOWN);
        actions.put(S, Direction.DOWN);
    }

    public static Map<Integer, Direction> getKeyActions() {
        setMoveActions();
        return actions;
    }
}
