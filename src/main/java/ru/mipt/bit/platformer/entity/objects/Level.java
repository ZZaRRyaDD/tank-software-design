package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.Direction;

import java.util.List;

public class Level {
    private final List<AbstractMovableLevelObject> movable;
    private final List<AbstractUnmovableLevelObject> unmovable;
    private final Integer height;
    private final Integer width;

    public Level(List<AbstractMovableLevelObject> movable, List<AbstractUnmovableLevelObject> unmovable, Integer height, Integer width) {
        this.movable = movable;
        this.unmovable = unmovable;
        this.height = height;
        this.width = width;
    }

    public void moveLevelObject(Direction direction, AbstractMovableLevelObject obj) {
        GridPoint2 point = new GridPoint2(obj.getCoordinates());
        point.add(direction.getDirectionPoint());
        obj.turn(direction);
        if (isFreePoint(point) && intoMapBorder(point)) {
            obj.move(direction);
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

    public boolean intoMapBorder(GridPoint2 point) {
        return (0 <= point.x && point.x < width) && (0 <= point.y && point.y < height);
    }

    public List<AbstractMovableLevelObject> getMovable() {
        return movable;
    }

    public AbstractMovableLevelObject getFirstMovable() {
        return movable.get(0);
    }

    public List<AbstractUnmovableLevelObject> getUnmovable() {
        return unmovable;
    }
}
