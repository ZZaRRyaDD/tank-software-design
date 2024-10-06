package ru.mipt.bit.platformer.playerinput;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.playerinput.actions.EmptyAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.keys.KeyActions;

import java.util.Map;

public class PlayerInput {
    public static AbstractAction getAction() {
        Map<Integer, AbstractAction> actions = KeyActions.getKeyActions();
        for (Map.Entry<Integer, AbstractAction> entry : actions.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return new EmptyAction();
    }
}
