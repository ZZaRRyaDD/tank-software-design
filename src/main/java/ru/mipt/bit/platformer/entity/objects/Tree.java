package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.UnmovableLevelObject;


public class Tree extends UnmovableLevelObject {
    public Tree(GridPoint2 point) {
        coordinates = point;
    }
}
