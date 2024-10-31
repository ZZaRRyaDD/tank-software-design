package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class Level {
    private final List<Tank> movable;
    private final List<Obstacle> unmovable;
    private final Integer height;
    private final Integer width;

    public Level(List<Tank> movable, List<Obstacle> unmovable, Integer height, Integer width) {
        this.movable = movable;
        this.unmovable = unmovable;
        this.height = height;
        this.width = width;
    }

    public List<Tank> getMovable() {
        return movable;
    }

    public Tank getPlayerMovable() {
        return movable.get(0);
    }

    public List<Tank> getBotsMovable() {
        return movable.subList(1, movable.size());
    }

    public List<Obstacle> getUnmovable() {
        return unmovable;
    }

    public boolean isFreePoint(GridPoint2 point) {
        return checkUnmovableObjects(point) && checkMovableObjects(point);
    }

    public boolean intoMapBorder(GridPoint2 point) {
        return (0 <= point.x && point.x < width) && (0 <= point.y && point.y < height);
    }

    private boolean checkMovableObjects(GridPoint2 point) {
        for (Tank obj : getMovable()) {
            if (obj.getCoordinates().equals(point) || obj.getDestinationCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUnmovableObjects(GridPoint2 point) {
        for (Obstacle obj : getUnmovable()) {
            if (obj.getCoordinates().equals(point)) {
                return false;
            }
        }
        return true;
    }


    public void updateState(float deltaTime) {
        for (Tank obj : getMovable()) {
            obj.updateState(deltaTime);
        }

        for (Obstacle obj : getUnmovable()) {
            obj.updateState(deltaTime);
        }
    }
}