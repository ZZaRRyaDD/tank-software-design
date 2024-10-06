package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;


public class Tree extends AbstractUnmovableLevelObject {
    public Tree(GridPoint2 point) {
        coordinates = point;
    }
}
