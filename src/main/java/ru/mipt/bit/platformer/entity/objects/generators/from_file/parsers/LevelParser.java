package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers;

import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Obstacle;

import java.util.List;

public interface LevelParser {
    void parse(List<String> lines);
    List<Tank> getMovableLevelObjects();
    List<Obstacle> getUnmovableLevelObjects();
    Integer getHeight();
    Integer getWidth();
}
