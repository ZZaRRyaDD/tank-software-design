package ru.mipt.bit.platformer.playerinput.inputs.ai;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.InputActionListener;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.*;

public class AIInput implements InputActionListener {
    private final InputActions inputActions;
    private final Level level;

    public AIInput(InputActions actions, Level level) {
        this.inputActions = actions;
        this.level = level;
    }

    public AbstractAction getAction(AbstractMovableLevelObject object) {
        List<AbstractActionFactory> actions = new ArrayList<>(inputActions.getKeyActions().values());
        return actions.get(new Random().nextInt(actions.size())).create(level, object);
    }
}
