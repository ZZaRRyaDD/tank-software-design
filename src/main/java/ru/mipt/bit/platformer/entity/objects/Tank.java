package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.*;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, Livable, Movable, Shootable, Damaged {
    private final GridPoint2 destinationCoordinates;
    private final GridPoint2 coordinates;
    private Direction direction;
    private final Level level;
    private int health;
    private float movementProgress = 1f;

    public Tank(GridPoint2 point, int health, Level level) {
        this.direction = Direction.UP;
        this.destinationCoordinates = point;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.health = health;
        this.level = level;
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

    private boolean isDead() {
        return health <= 0;
    }

    @Override
    public void updateState(float deltaTime) {
        if (isDead()) {
            level.deleteGameObject(this);
        } else {
            movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
            if (isMoving()) {
                coordinates.set(destinationCoordinates);
            }
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

    @Override
    public void shoot() {
        GridPoint2 startPointBullet = getCoordinates().cpy().add(getDirection().getDirectionPoint());
        Bullet bullet = new Bullet(
                10,
                startPointBullet,
                getDirection(),
                level
        );
        level.addGameObject(bullet);
    }

    @Override
    public void takeDamage(float damage) {
        health -= damage;
    }
}
