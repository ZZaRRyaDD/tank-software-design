package ru.mipt.bit.platformer.entity.drawers.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;

import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.drawers.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.drawers.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.HashMap;
import java.util.Map;

public class LevelDrawer implements LevelGraphic {
    private final Batch batch;
    private final TiledMap map;
    private final MapRenderer renderer;
    private final Map<Class<? extends GameObject>, GraphicFactory> strategyGraphics = new HashMap<>();
    private final Map<GameObject, GameObjectGraphic> graphicObjects = new HashMap<>();

    public LevelDrawer(TiledMap map, MapRenderer renderer, Batch batch) {
        this.map = map;
        this.renderer = renderer;
        this.batch = batch;
    }

    public Batch getBatch() {
        return batch;
    }

    @Override
    public void addStrategyGraphics(Class<? extends GameObject> clazz, GraphicFactory gameObjectGraphic) {
        strategyGraphics.put(clazz, gameObjectGraphic);
    }

    public Map<GameObject, GameObjectGraphic> getGraphicObjects() {
        return graphicObjects;
    }

    public void renderObjects() {
        for (GameObjectGraphic drawer : graphicObjects.values()) {
            drawer.draw(getBatch());
        }
    }

    public void render() {
        renderer.render();
        batch.begin();
        renderObjects();
        batch.end();
    }

    public void dispose() {
        for (GameObjectGraphic drawer : graphicObjects.values()) {
            drawer.dispose();
        }

        map.dispose();
        batch.dispose();
    }

    @Override
    public void onAddGameObject(GameObject object) {
        GameObjectGraphic drawer = strategyGraphics.get(object.getClass()).create(object);
        graphicObjects.put(object, drawer);
    }

    @Override
    public void onDeleteGameObject(GameObject object) {
        graphicObjects.remove(object).dispose();
    }
}
