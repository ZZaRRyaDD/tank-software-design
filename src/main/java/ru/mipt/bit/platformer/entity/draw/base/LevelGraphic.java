package ru.mipt.bit.platformer.entity.draw.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.entity.objects.Tank;

import java.util.List;
import java.util.Map;

public interface LevelGraphic {
    void render();
    void dispose();
    Batch getBatch();
    Map<Tank, GameObjectGraphic> getMovableDrawers();
}
