package ru.mipt.bit.platformer.entity.objects.generators.from_file;

import ru.mipt.bit.platformer.entity.listener.LevelListener;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.LevelReader;

import java.util.*;

public class FromFileLevelGenerator implements LevelGenerator {
    private final LevelReader fileReader;
    private final LevelParser fileParser;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Tank> tanks = new ArrayList<>();
    private Level level;
    private final List<LevelListener> listeners;

    public FromFileLevelGenerator(LevelReader fileReader, LevelParser fileParser, List<LevelListener> listeners) {
        this.fileReader = fileReader;
        this.fileParser = fileParser;
        this.listeners = listeners;
    }
    @Override
    public void generate() {
        List<String> lines = fileReader.read();
        fileParser.parse(lines);

        level = new Level(fileParser.getHeight(), fileParser.getWidth(), listeners);
        createTanks();
        createObstacles();
    }

    private void createTanks() {
        fileParser.getTanksPoints().forEach(tankPoint -> tanks.add(new Tank(tankPoint, 100, level)));
        tanks.forEach(tank -> level.addGameObject(tank));
    }

    private void createObstacles() {
        fileParser.getObstaclesPoints().forEach(obstaclePoint -> obstacles.add(new Obstacle(obstaclePoint)));
        obstacles.forEach(obstacle -> level.addGameObject(obstacle));
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Tank getPlayerTank() {
        return tanks.get(0);
    }

    @Override
    public List<Tank> getAITanks() {
        return tanks.subList(1, tanks.size());
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
