package ru.tests;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.tank.Direction;

public class TankTest {
    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testTankMove(Direction direction){
        Tank tank = new Tank(new GridPoint2(1, 0));
        tank.move(direction.directionPoint, direction.directionRotation);

        float deltaTime = 1f;

        tank.updateState(deltaTime);

        assertEquals(tank.getCoordinates(), tank.getDestinationCoordinates());
    }
}
