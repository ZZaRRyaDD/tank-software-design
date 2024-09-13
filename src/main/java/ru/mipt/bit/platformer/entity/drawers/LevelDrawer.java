package ru.mipt.bit.platformer.entity.drawers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.math.MathUtils.isEqual;

import ru.mipt.bit.platformer.entity.tree.Tree;
import ru.mipt.bit.platformer.entity.level.Level;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawer implements GraphicObject {

    private Batch batch;
    private String filePath;
    private TiledMap map;
    private MapRenderer renderer;
    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;
    private Level level;


    public LevelDrawer(String filePath, Batch batch, Level level) {
        this.filePath = filePath;
        this.batch = batch;
        this.level = level;
    }

    public void draw() {
        this.map = new TmxMapLoader().load(this.filePath);
        this.renderer = createSingleLayerMapRenderer(this.map, this.batch);
        this.groundLayer = getSingleLayer(this.map);
        this.tileMovement = new TileMovement(this.groundLayer, Interpolation.smooth);
        System.out.println(this.tileMovement);
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public void renderTiles() {
        renderer.render();
    }

    public void renderMoves(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(level.getTank().drawer.getRectangle(), level.getTank().getCoordinates(), level.getTank().getDestinationCoordinates(), level.getTank().getMovementProgress());

        level.getTank().setMovementProgress(continueProgress(level.getTank().getMovementProgress(), deltaTime, level.getMovementSpeed()));
        if (isEqual(level.getTank().getMovementProgress(), 1f)) {
            level.getTank().setCoordinates(level.getTank().getDestinationCoordinates());
        }

        renderTiles();
    }

    public void renderObjects() {
        drawTextureRegionUnscaled(batch, level.getTank().drawer.getGraphics(), level.getTank().drawer.getRectangle(), level.getTank().getRotation());
        for (Tree tree : level.getTress()) {
            drawTextureRegionUnscaled(batch, tree.drawer.getGraphics(), tree.drawer.getRectangle(), 0f);
        }
    }

    public void recordDrawCommand() {
        // start recording all drawing commands
        batch.begin();
        renderObjects();
        // submit all drawing requests
        batch.end();
    }

    public void dispose() {
        level.getTank().drawer.dispose();
        for (Tree tree : level.getTress()) {
            tree.drawer.dispose();
        }
        map.dispose();
        batch.dispose();
    }
}
