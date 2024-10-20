package ru.mipt.bit.platformer.playerinput.actions.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

public class MoveAction implements AbstractAction {
    private final Direction direction;
    private final Level level;
    private final AbstractMovableLevelObject object;
    private final int width;
    private final int height;

    public MoveAction(Direction direction, Level level, AbstractMovableLevelObject object) {
        this.direction = direction;
        this.level = level;
        this.object = object;
        this.width = level.getWidth();
        this.height = level.getHeight();
    }

    public void apply() {
        GridPoint2 point = new GridPoint2(object.getCoordinates());
        point.add(direction.getDirectionPoint());

        if (intoMapBorder(point) && isFreePoint(point)) {
            object.move(direction);
        }
    }

    public boolean isFreePoint(GridPoint2 point) {
        return checkUnmovableObjects(point) && checkMovableObjects(point);
    }

    public boolean intoMapBorder(GridPoint2 point) {
        return (0 <= point.x && point.x < width) && (0 <= point.y && point.y < height);
    }


    private boolean checkMovableObjects(GridPoint2 point) {
        for (AbstractMovableLevelObject obj : level.getMovable()) {
            if (obj.getCoordinates().equals(point) || obj.getDestinationCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUnmovableObjects(GridPoint2 point) {
        for (AbstractUnmovableLevelObject obj : level.getUnmovable()) {
            if (obj.getCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }
}
