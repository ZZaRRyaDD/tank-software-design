package ru.mipt.bit.platformer.playerinput.inputs.keyboard_player;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.actions.factories.EmptyActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.ActionGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.*;

public class KeyboardPlayerInput implements ActionGenerator {
    private final InputActions inputActions;
    private final Level level;

    public KeyboardPlayerInput(InputActions actions, Level level) {
        this.inputActions = actions;
        this.level = level;
    }

    @Override
    public List<AbstractAction> getActionList() {
        return new ArrayList<>(Collections.singletonList(getAction(level.getPlayerMovable())));
    }

    @Override
    public AbstractAction getAction(AbstractMovableLevelObject object) {
        for (Map.Entry<Integer, AbstractActionFactory> entry : inputActions.getKeyActions().entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue().create(object);
            }
        }
        return new EmptyActionFactory().create(object);
    }
}
