package ru.mipt.bit.platformer.entity.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.entity.objects.*;
import ru.mipt.bit.platformer.entity.objects.base.MovableLevelObject;
import ru.mipt.bit.platformer.entity.objects.base.UnmovableLevelObject;
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
    private Level level;
    private final HashMap<MovableLevelObject, LevelObjectDrawer> movableDrawers = new HashMap<>();
    private final HashMap<UnmovableLevelObject, LevelObjectDrawer> unmovableDrawers = new HashMap<>();


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
        for (MovableLevelObject obj : level.getMovable()) {
            LevelObjectDrawer tankDrawerObject = new LevelObjectDrawer("images/blueTank.png");
            movableDrawers.put(obj, tankDrawerObject);
            tankDrawerObject.draw();
        }
    }

    public void drawUnmovable() {
        for (UnmovableLevelObject obj : level.getUnmovable()) {
            LevelObjectDrawer treeDrawerObject = new LevelObjectDrawer("images/greenTree.png");
            unmovableDrawers.put(obj, treeDrawerObject);
            treeDrawerObject.draw();
            moveRectangleAtTileCenter(groundLayer, treeDrawerObject.getRectangle(), obj.getCoordinates());
        }
    }

    public void renderMoves(float deltaTime) {
        float movementSpeed = 0.4f;
        for (Map.Entry<MovableLevelObject, LevelObjectDrawer> entry : movableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            MovableLevelObject movableObject = entry.getKey();
            tileMovement.moveRectangleBetweenTileCenters(drawer.getRectangle(), movableObject.getCoordinates(), movableObject.getDestinationCoordinates(), movableObject.getMovementProgress());
            movableObject.updateState(deltaTime, movementSpeed);
        }
        renderer.render();
    }

    public void renderObjects() {
        for (Map.Entry<MovableLevelObject, LevelObjectDrawer> entry : movableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            MovableLevelObject movableObject = entry.getKey();
            drawTextureRegionUnscaled(batch, drawer.getGraphics(), drawer.getRectangle(), movableObject.getRotation());
        }
        for (Map.Entry<UnmovableLevelObject, LevelObjectDrawer> entry : unmovableDrawers.entrySet()) {
            LevelObjectDrawer drawer = entry.getValue();
            UnmovableLevelObject unmovableObject = entry.getKey();
            drawTextureRegionUnscaled(batch, drawer.getGraphics(), drawer.getRectangle(), 0f);
        }
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
