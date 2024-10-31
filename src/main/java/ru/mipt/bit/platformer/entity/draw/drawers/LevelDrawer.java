package ru.mipt.bit.platformer.entity.draw.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.objects.*;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawer implements LevelGraphic {
    private final Batch batch;
    private final String filePath;
    private TiledMap map;
    private MapRenderer renderer;
    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;
    private final Level level;
    private final Map<Tank, GameObjectGraphic> movableDrawers = new HashMap<>();
    private final Map<Obstacle, GameObjectGraphic> unmovableDrawers = new HashMap<>();

    public LevelDrawer(String filePath, Batch batch, Level level) {
        this.filePath = filePath;
        this.batch = batch;
        this.level = level;
        this.batch.begin();
        drawLevel();
        drawMovable();
        drawUnmovable();
        this.batch.end();
    }

    public void drawLevel() {
        this.map = new TmxMapLoader().load(this.filePath);
        this.renderer = createSingleLayerMapRenderer(this.map, this.batch);
        this.groundLayer = getSingleLayer(this.map);
        this.tileMovement = new TileMovement(this.groundLayer, Interpolation.smooth);
    }

    public void drawMovable() {
        for (Tank obj : level.getMovable()) {
            GameObjectGraphic movableDrawer = new TankDrawer("images/blueTank.png", obj, this.tileMovement);
            movableDrawers.put(obj, movableDrawer);
            movableDrawer.draw(getBatch());
        }
    }

    public void drawUnmovable() {
        for (Obstacle obj : level.getUnmovable()) {
            GameObjectGraphic unmovableDrawer = new ObstacleDrawer("images/greenTree.png", obj, groundLayer);
            unmovableDrawers.put(obj, unmovableDrawer);
            unmovableDrawer.draw(getBatch());
        }
    }

    public Map<Tank, GameObjectGraphic> getMovableDrawers() {
        return movableDrawers;
    }

    public Batch getBatch() {
        return batch;
    }

    private void renderMovableObjects() {
        for (GameObjectGraphic drawer : movableDrawers.values()) {
            drawer.draw(getBatch());
        }
    }

    private void renderUnmovableObjects() {
        for (GameObjectGraphic drawer : unmovableDrawers.values()) {
            drawer.draw(getBatch());
        }
    }

    public void renderObjects() {
        renderMovableObjects();
        renderUnmovableObjects();
    }

    public void render() {
        renderer.render();
        batch.begin();
        renderObjects();
        batch.end();
    }

    public void dispose() {
        for (GameObjectGraphic drawer : movableDrawers.values()) {
            drawer.dispose();
        }

        for (GameObjectGraphic drawer : unmovableDrawers.values()) {
            drawer.dispose();
        }

        map.dispose();
        batch.dispose();
    }
}
