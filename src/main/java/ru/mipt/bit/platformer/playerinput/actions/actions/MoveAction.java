package ru.mipt.bit.platformer.playerinput.actions.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

public class MoveAction implements AbstractAction {
    private final Direction direction;
    private final Level level;
    private final Tank object;

    public MoveAction(Direction direction, Level level, Tank object) {
        this.direction = direction;
        this.level = level;
        this.object = object;
    }

    public void apply() {
        GridPoint2 point = new GridPoint2(object.getCoordinates());
        point.add(direction.getDirectionPoint());
        boolean hasWay = level.intoMapBorder(point) && level.isFreePoint(point);

        object.move(direction, hasWay);
    }
}
