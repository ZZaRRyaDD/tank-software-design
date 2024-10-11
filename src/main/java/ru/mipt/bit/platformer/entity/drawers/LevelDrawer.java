package ru.mipt.bit.platformer.entity.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.entity.objects.*;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.AbstractUnmovableLevelObject;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawer implements GraphicObject {

    private Batch batch;
    private String filePath;
    private TiledMap map;
    private MapRenderer renderer;
    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;
    private final Level level;
    private final HashMap<AbstractMovableLevelObject, LevelObjectDrawer> movableDrawers = new HashMap<>();
    private final HashMap<AbstractUnmovableLevelObject, LevelObjectDrawer> unmovableDrawers = new HashMap<>();


    public LevelDrawer(String filePath, Batch batch, Level level) {
        this.filePath = filePath;
        this.batch = batch;
        this.level = level;
    }

    public void draw() {
        drawLevel();
        drawMovable();
        drawUnmovable();
    }

    public void drawLevel() {
        this.map = new TmxMapLoader().load(this.filePath);
        this.renderer = createSingleLayerMapRenderer(this.map, this.batch);
        this.groundLayer = getSingleLayer(this.map);
        this.tileMovement = new TileMovement(this.groundLayer, Interpolation.smooth);
    }

    public void drawMovable() {
        for (AbstractMovableLevelObject obj : level.getMovable()) {
            LevelObjectDrawer tankDrawerObject = new LevelObjectDrawer("images/blueTank.png");
            movableDrawers.put(obj, tankDrawerObject);
            tankDrawerObject.draw();
        }
    }

    public void drawUnmovable() {
        for (AbstractUnmovableLevelObject obj : level.getUnmovable()) {
            LevelObjectDrawer treeDrawerObject = new LevelObjectDrawer("images/greenTree.png");
            unmovableDrawers.put(obj, treeDrawerObject);
            treeDrawerObject.draw();
            moveRectangleAtTileCenter(groundLayer, treeDrawerObject.getRectangle(), obj.getCoordinates());
        }
    }

    public void renderMoves(float deltaTime) {
        float movementSpeed = 0.4f;
        for (Map.Entry<AbstractMovableLevelObject, LevelObjectDrawer> entry : movableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            AbstractMovableLevelObject movableObject = entry.getKey();
            tileMovement.moveRectangleBetweenTileCenters(drawer.getRectangle(), movableObject.getCoordinates(), movableObject.getDestinationCoordinates(), movableObject.getMovementProgress());
            movableObject.updateState(deltaTime, movementSpeed);
        }
        renderer.render();
    }

    private void renderMovableObjects() {
        for (Map.Entry<AbstractMovableLevelObject, LevelObjectDrawer> entry : movableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            AbstractMovableLevelObject movableObject = entry.getKey();
            drawTextureRegionUnscaled(batch, drawer.getGraphics(), drawer.getRectangle(), movableObject.getRotation());
        }
    }

    private void renderUnmovableObjects() {
        for (Map.Entry<AbstractUnmovableLevelObject, LevelObjectDrawer> entry : unmovableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            drawTextureRegionUnscaled(batch, drawer.getGraphics(), drawer.getRectangle(), 0f);
        }
    }

    public void renderObjects() {
        renderMovableObjects();
        renderUnmovableObjects();
    }

    public void recordDrawCommand() {
        batch.begin();
        renderObjects();
        batch.end();
    }

    public void dispose() {
        for (LevelObjectDrawer drawer : movableDrawers.values()) {
            drawer.dispose();
        }

        for (LevelObjectDrawer drawer : unmovableDrawers.values()) {
            drawer.dispose();
        }

        map.dispose();
        batch.dispose();
    }
}
