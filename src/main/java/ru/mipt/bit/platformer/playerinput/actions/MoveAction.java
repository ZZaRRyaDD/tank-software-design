package ru.mipt.bit.platformer.playerinput.actions;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.Direction;

public class MoveAction implements AbstractAction {
    private final Direction direction;
    
    public MoveAction(Direction direction) {
        this.direction = direction;
    }

    public void apply(Level level, AbstractMovableLevelObject object) {
        level.moveLevelObject(direction, object);
    }
}
