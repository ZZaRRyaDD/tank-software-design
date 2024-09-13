package ru.mipt.bit.platformer.entity.tank;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;

import ru.mipt.bit.platformer.entity.drawers.LevelObjectDrawer;

public class Tank {

    public LevelObjectDrawer drawer;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Tank(String imagePath, int coordinateX, int coordinateY) {
        this.drawer = new LevelObjectDrawer(imagePath);
        this.drawer.draw();

        this.destinationCoordinates = new GridPoint2(coordinateX, coordinateY);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = 0f;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public void setCoordinates(GridPoint2 point) {
        coordinates.set(point);
    }

    public void move(GridPoint2 point, float rotation) {
        destinationCoordinates.add(point.x, point.y);
        this.rotation = rotation;
        movementProgress = 0f;
    }
}
