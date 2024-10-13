package ru.mipt.bit.platformer.entity.objects.generators.from_file;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.LevelReader;

import java.util.*;

public class FromFileLevelGenerator implements LevelGenerator {

    private final LevelReader fileReader;
    private final LevelParser fileParser;

    public FromFileLevelGenerator(LevelReader fileReader, LevelParser fileParser) {
        this.fileReader = fileReader;
        this.fileParser = fileParser;
    }
    @Override
    public Level generate() {
        List<String> lines = fileReader.read();
        fileParser.parse(lines);

        return new Level(
            fileParser.getMovableLevelObjects(),
            fileParser.getUnmovableLevelObjects(),
            fileParser.getHeight(),
            fileParser.getWidth()
        );
    }
}
