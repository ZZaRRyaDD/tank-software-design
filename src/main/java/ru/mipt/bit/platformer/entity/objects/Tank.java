package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends AbstractMovableLevelObject {

    public Tank(GridPoint2 point) {
        this.destinationCoordinates = point;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
    }

    private boolean isNotMoving() {
        return isEqual(movementProgress, MOVEMENT_PROGRESS_MOVE);
    }

    public void updateState(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isNotMoving()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void turn(Direction direction) {
        rotation = direction.getDirectionRotation();
    }

    public void move(Direction direction) {
        if (isNotMoving()) {
            turn(direction);
            destinationCoordinates.add(direction.getDirectionPoint());
            movementProgress = MOVEMENT_PROGRESS_TURN;
        }
    }
}
