package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.base.Livable;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Livable {
    private static final float MOVEMENT_PROGRESS_MOVE = 1f;
    private static final float MOVEMENT_PROGRESS_TURN = 0f;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float movementSpeed = 0.8f;
    private GridPoint2 coordinates;
    private Direction direction;
    private int health;

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public Tank(GridPoint2 point, int health) {
        this.direction = Direction.UP;
        this.destinationCoordinates = point;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.health = health;
    }

    private boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_PROGRESS_MOVE);
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void move(Direction direction, boolean hasWay) {
        if (isMoving()) {
            if (hasWay) {
                destinationCoordinates.add(direction.getDirectionPoint());
            }
            this.direction = direction;
            movementProgress = MOVEMENT_PROGRESS_TURN;
        }
    }

    @Override
    public float getHealth() {
        return health;
    }
}
