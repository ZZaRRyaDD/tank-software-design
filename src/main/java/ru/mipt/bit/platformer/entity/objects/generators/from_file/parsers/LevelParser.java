package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Obstacle;

import java.util.List;

public interface LevelParser {
    void parse(List<String> lines);
    List<GridPoint2> getTanksPoints();
    List<GridPoint2> getObstaclesPoints();
    Integer getHeight();
    Integer getWidth();
}
