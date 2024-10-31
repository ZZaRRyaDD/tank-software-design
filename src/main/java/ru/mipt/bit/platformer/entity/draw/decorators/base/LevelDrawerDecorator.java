package ru.mipt.bit.platformer.entity.draw.decorators.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.objects.Tank;

import java.util.Map;

public abstract class LevelDrawerDecorator implements LevelGraphic {
    protected LevelGraphic levelGraphics;

    @Override
    public void render() {
        levelGraphics.render();
    }

    @Override
    public void dispose() {
        levelGraphics.dispose();
    }

    @Override
    public Batch getBatch() {
        return levelGraphics.getBatch();
    }

    @Override
    public Map<Tank, GameObjectGraphic> getMovableDrawers() {
        return levelGraphics.getMovableDrawers();
    }
}
