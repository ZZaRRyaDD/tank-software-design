package ru.mipt.bit.platformer.entity.objects;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;

import java.util.List;

public class Level {
    private final List<AbstractMovableLevelObject> movable;
    private final List<AbstractUnmovableLevelObject> unmovable;
    private final Integer height;
    private final Integer width;

    public Level(List<AbstractMovableLevelObject> movable, List<AbstractUnmovableLevelObject> unmovable, Integer height, Integer width) {
        this.movable = movable;
        this.unmovable = unmovable;
        this.height = height;
        this.width = width;
    }

    public List<AbstractMovableLevelObject> getMovable() {
        return movable;
    }

    public AbstractMovableLevelObject getPlayerMovable() {
        return movable.get(0);
    }

    public List<AbstractMovableLevelObject> getBotsMovable() {
        return movable.subList(1, movable.size());
    }

    public List<AbstractUnmovableLevelObject> getUnmovable() {
        return unmovable;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }
}
