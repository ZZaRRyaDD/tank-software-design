package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.actions.MoveAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

public class MoveActionFactory implements AbstractActionFactory {
    private final Direction direction;

    public MoveActionFactory(Direction direction) {
        this.direction = direction;
    }

    @Override
    public AbstractAction create(Level level, AbstractMovableLevelObject object) {
        return new MoveAction(direction, level, object);
    }
}
