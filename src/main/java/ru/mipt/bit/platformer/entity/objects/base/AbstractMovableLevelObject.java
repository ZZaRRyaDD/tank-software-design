package ru.mipt.bit.platformer.entity.objects.base;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.Direction;

public abstract class AbstractMovableLevelObject extends AbstractLevelObject {
    protected float rotation = 0f;
    protected static final float MOVEMENT_PROGRESS_MOVE = 1f;
    protected static final float MOVEMENT_PROGRESS_TURN = 0f;
    protected GridPoint2 destinationCoordinates;
    protected float movementProgress = 1f;

    public float getRotation() {
        return rotation;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public abstract void updateState(float deltaTime, float movementSpeed);

    public abstract void move(Direction direction);
    public abstract void turn(Direction direction);
}
