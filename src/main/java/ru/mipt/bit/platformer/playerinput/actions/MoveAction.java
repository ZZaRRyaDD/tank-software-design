package ru.mipt.bit.platformer.playerinput.actions;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.keys.Direction;

public class MoveAction extends AbstractAction {
    private final Direction direction;
    
    public MoveAction(Direction direction) {
        this.direction = direction;
    }

    public void apply(Level level) {
        level.moveLevelObject(direction);
    }
}
