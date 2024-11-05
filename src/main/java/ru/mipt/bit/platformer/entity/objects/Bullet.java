package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.Damaged;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements GameObject {
    float MOVEMENT_PROGRESS_MOVE = 1f;
    float MOVEMENT_PROGRESS_TURN = 0f;
    float movementSpeed = 0.8f;

    private float damage;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private Level level;
    private float movementProgress = 1f;

    public Bullet(float damage, GridPoint2 coordinates, Direction direction, Level level) {
        this.damage = damage;
        this.coordinates = coordinates;
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.direction = direction;
        this.level = level;
    }

    @Override
    public void updateState(float deltaTime) {
        hit();
        move();
        updateProgressState(deltaTime);
    }

    private void hit() {
        if (isHit()){
            level.deleteGameObject(this);
            applyDamage();
        }
    }

    private void applyDamage() {
        GameObject object = level.gatGameObjectByCoordinates(coordinates);
        if (object instanceof Damaged){
            ((Damaged) object).takeDamage(damage);
        }
    }

    private boolean isHit() {
        return level.gatGameObjectByCoordinates(coordinates) != null;
    }

    private boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_PROGRESS_MOVE);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean isBusyCoordinate(GridPoint2 point) {
        return coordinates.equals(point) || destinationCoordinates.equals(point);
    }

    private void updateProgressState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates.set(destinationCoordinates);
        }
        System.out.println(coordinates);
    }

    public void move() {
        destinationCoordinates.add(direction.getDirectionPoint());
    }
}
