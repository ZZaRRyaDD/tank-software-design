package ru.mipt.bit.platformer.playerinput.inputs.ai;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.ActionGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.*;

public class AI implements ActionGenerator {
    private final InputActions inputActions;
    private final Level level;

    public AI(InputActions actions, Level level) {
        this.inputActions = actions;
        this.level = level;
    }

    @Override
    public AbstractAction getAction(AbstractMovableLevelObject object) {
        List<AbstractActionFactory> actions = new ArrayList<>(inputActions.getKeyActions().values());
        return actions.get(new Random().nextInt(actions.size())).create(object);
    }

    @Override
    public List<AbstractAction> getActionList() {
        List<AbstractAction> actions = new ArrayList<>();
        for (AbstractMovableLevelObject object : level.getBotsMovable()) {
            actions.add(getAction(object));
        }
        return actions;
    }
}
