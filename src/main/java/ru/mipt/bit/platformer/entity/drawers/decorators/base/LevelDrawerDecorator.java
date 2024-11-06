package ru.mipt.bit.platformer.entity.drawers.decorators.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.drawers.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.drawers.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

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
    public void addStrategyGraphics(Class<? extends GameObject> clazz, GraphicFactory graphicsFactory) {
        levelGraphics.addStrategyGraphics(clazz, graphicsFactory);
    }

    @Override
    public Map<GameObject, GameObjectGraphic> getGraphicObjects() {
        return levelGraphics.getGraphicObjects();
    }

    @Override
    public void onAddGameObject(GameObject object) {
        levelGraphics.onAddGameObject(object);
    }

    @Override
    public void onDeleteGameObject(GameObject object) {
        levelGraphics.onDeleteGameObject(object);
    }
}
