package ru.mipt.bit.platformer.entity.objects.base;

import com.badlogic.gdx.math.GridPoint2;

public abstract class LevelObject {
    protected GridPoint2 coordinates;

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
