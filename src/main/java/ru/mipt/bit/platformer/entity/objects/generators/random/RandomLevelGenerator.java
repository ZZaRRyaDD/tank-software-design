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
        Random random = new Random();

        List<AbstractUnmovableLevelObject> unmovable = generateUnmovableObjects(5, random, points);
        List<AbstractMovableLevelObject> movable = generateMovableObjects(1, random, points);
        return new Level(movable, unmovable, height, width);
    }

    public List<AbstractUnmovableLevelObject> generateUnmovableObjects(int countObjects, Random random, Set<GridPoint2> points) {
        List<AbstractUnmovableLevelObject> unmovable = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = new GridPoint2(random.nextInt(height), random.nextInt(width));
            while (points.contains(point)) {
                point = new GridPoint2(random.nextInt(height), random.nextInt(width));
            }
            unmovable.add(new Tree(point));
        }
        return unmovable;
    }

    public List<AbstractMovableLevelObject> generateMovableObjects(int countObjects, Random random, Set<GridPoint2> points) {
        List<AbstractMovableLevelObject> movable = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = new GridPoint2(random.nextInt(height), random.nextInt(width));
            while (points.contains(point)) {
                point = new GridPoint2(random.nextInt(height), random.nextInt(width));
            }
            movable.add(new Tank(point));
        }
        return movable;
    }
}
