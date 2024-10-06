package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.playerinput.keys.Direction;

import java.util.List;

public class Level {
    private final List<AbstractMovableLevelObject> movable;
    private final List<AbstractUnmovableLevelObject> unmovable;

    public Level(List<AbstractMovableLevelObject> movable, List<AbstractUnmovableLevelObject> unmovable) {
        this.movable = movable;
        this.unmovable = unmovable;
    }

    public void moveLevelObject(Direction direction) {
        for (AbstractMovableLevelObject obj : movable) {
            GridPoint2 point = new GridPoint2(obj.getCoordinates());
            point.add(direction.getDirectionPoint());
            if (isFreePoint(point)) {
                obj.move(direction);
            }
        }
    }

    public boolean isFreePoint(GridPoint2 point) {
        for (AbstractUnmovableLevelObject obj : unmovable) {
            if (obj.getCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }

    public List<AbstractMovableLevelObject> getMovable() {
        return movable;
    }

    public List<AbstractUnmovableLevelObject> getUnmovable() {
        return unmovable;
    }
}
