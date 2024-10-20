package ru.mipt.bit.platformer.entity.objects.generators.random;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Tree;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;

import java.util.*;

public class RandomLevelGenerator implements LevelGenerator {

    private final int height;
    private final int width;
    private final Set<GridPoint2> points = new HashSet<>();

    public RandomLevelGenerator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Level generate() {
        points.clear();
        Random random = new Random();

        List<AbstractUnmovableLevelObject> unmovable = generateUnmovableObjects(5, random);
        List<AbstractMovableLevelObject> movable = generateMovableObjects(3, random);
        return new Level(movable, unmovable, height, width);
    }

    private GridPoint2 getRandomPoint(Random random) {
        return new GridPoint2(random.nextInt(width - 1), random.nextInt(height - 1));
    }

    private boolean checkExistPoint(GridPoint2 point) {
        return points.contains(point);
    }

    private GridPoint2 getPoint(Random random) {
        GridPoint2 point = getRandomPoint(random);
        while (checkExistPoint(point)) {
            point = getRandomPoint(random);
        }
        points.add(point);
        return point;
    }

    public List<AbstractUnmovableLevelObject> generateUnmovableObjects(int countObjects, Random random) {
        List<AbstractUnmovableLevelObject> unmovable = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = getPoint(random);
            unmovable.add(new Tree(point));
        }
        return unmovable;
    }

    public List<AbstractMovableLevelObject> generateMovableObjects(int countObjects, Random random) {
        List<AbstractMovableLevelObject> movable = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = getPoint(random);
            movable.add(new Tank(point));
        }
        return movable;
    }
}
