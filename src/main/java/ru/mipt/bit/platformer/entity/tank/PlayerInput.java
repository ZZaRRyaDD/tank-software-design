package ru.mipt.bit.platformer.entity.tank;

import ru.mipt.bit.platformer.entity.tank.TankDirection;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;
import static com.badlogic.gdx.Input.Keys.*;

public class PlayerInput {
    public static TankDirection chooseDirection() {
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return TankDirection.RIGHT;
        } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return TankDirection.LEFT;
        } else if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return TankDirection.UP;
        } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return TankDirection.DOWN;
        }
        return TankDirection.NULL;
    }
}
