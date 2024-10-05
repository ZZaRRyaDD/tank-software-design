package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.objects.base.MovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.UnmovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.Direction;

import java.util.List;

public class Level {
    private final List<MovableLevelObject> movable;
    private final List<UnmovableLevelObject> unmovable;

    public Level(List<MovableLevelObject> movable, List<UnmovableLevelObject> unmovable) {
        this.movable = movable;
        this.unmovable = unmovable;
    }

    public void moveTank(Direction direction) {
        if (direction != Direction.NULL) {
            for (MovableLevelObject obj : movable) {
                GridPoint2 point = new GridPoint2(obj.getCoordinates());
                point.add(direction.getDirectionPoint());
                if (isFreePoint(point)) {
                    obj.move(direction);
                }
            }
        }
    }

    public boolean isFreePoint(GridPoint2 point) {
        for (UnmovableLevelObject obj : unmovable) {
            if (obj.getCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }

    public List<MovableLevelObject> getMovable() {
        return movable;
    }

    public List<UnmovableLevelObject> getUnmovable() {
        return unmovable;
    }
}
