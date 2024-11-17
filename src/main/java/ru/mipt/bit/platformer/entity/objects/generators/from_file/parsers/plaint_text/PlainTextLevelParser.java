package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.plaint_text;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;

import java.util.ArrayList;
import java.util.List;

public class PlainTextLevelParser implements LevelParser {

    private final List<GridPoint2> tanks = new ArrayList<>();
    private final List<GridPoint2> obstacles = new ArrayList<>();
    private Integer height;
    private Integer width;

    @Override
    public void parse(List<String> lines) {
        height = lines.size();
        width = lines.get(0).length();

        tanks.clear();
        obstacles.clear();
        for (int yCoordinate = height; yCoordinate > 0; yCoordinate--) {
            for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
                char c = lines.get(height - yCoordinate).charAt(xCoordinate);

                switch (c) {
                    case '_':
                        continue;
                    case 'T':
                        obstacles.add(new GridPoint2(xCoordinate, yCoordinate));
                        break;
                    case 'X':
                        tanks.add(new GridPoint2(xCoordinate, yCoordinate));
                        break;
                }
            }
        }
    }

    @Override
    public List<GridPoint2> getTanksPoints() {
        return tanks;
    }

    @Override
    public List<GridPoint2> getObstaclesPoints() {
        return obstacles;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Integer getWidth() {
        return width;
    }
}
