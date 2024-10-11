package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.plaint_text;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Tree;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;

import java.util.ArrayList;
import java.util.List;

public class PlainTextLevelParser implements LevelParser {

    List<AbstractMovableLevelObject> movable = new ArrayList<>();
    List<AbstractUnmovableLevelObject> unmovable = new ArrayList<>();

    @Override
    public void parse(List<String> lines) {
        movable.clear();
        unmovable.clear();
        int xCoordinate, yCoordinate = lines.size();
        for (String line : lines) {
            xCoordinate = 0;
            for (char c : line.toCharArray()) {
                xCoordinate++;
                switch (c) {
                    case '_':
                        continue;
                    case 'T':
                        unmovable.add(new Tree(new GridPoint2(xCoordinate, yCoordinate)));
                        break;
                    case 'X':
                        movable.add(new Tank(new GridPoint2(xCoordinate, yCoordinate)));
                        break;
                }
            }
            yCoordinate--;
        }
    }

    @Override
    public List<AbstractMovableLevelObject> getMovableLevelObjects() {
        return movable;
    }

    @Override
    public List<AbstractUnmovableLevelObject> getUnmovableLevelObjects() {
        return unmovable;
    }
}
