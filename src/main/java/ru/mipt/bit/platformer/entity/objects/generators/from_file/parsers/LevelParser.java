package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;

import java.util.List;

public interface LevelParser {
    void parse(List<String> lines);
    List<AbstractMovableLevelObject> getMovableLevelObjects();
    List<AbstractUnmovableLevelObject> getUnmovableLevelObjects();
}
