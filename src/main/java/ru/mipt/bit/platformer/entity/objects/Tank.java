package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.base.Livable;
import ru.mipt.bit.platformer.entity.objects.base.Movable;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, Livable, Movable {
    private GridPoint2 destinationCoordinates;
    private GridPoint2 coordinates;
    private Direction direction;
    private int health;
    private float movementProgress = 1f;

    public Tank(GridPoint2 point, int health) {
        this.direction = Direction.UP;
        this.destinationCoordinates = point;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.health = health;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean isBusyCoordinate(GridPoint2 point) {
        return coordinates.equals(point) || destinationCoordinates.equals(point);
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    private boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_PROGRESS_MOVE);
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
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
