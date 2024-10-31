package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;


public class Obstacle {
    protected GridPoint2 coordinates;

    public Obstacle(GridPoint2 point) {
        coordinates = point;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void updateState(float deltaTime) {}
}
