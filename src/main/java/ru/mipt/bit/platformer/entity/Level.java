package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static com.badlogic.gdx.Input.Keys;
import static com.badlogic.gdx.Input.Keys.*;

import ru.mipt.bit.platformer.util.TileMovement;

import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.Tree;
import ru.mipt.bit.platformer.entity.GraphicObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level implements GraphicObject {
    private static final float MOVEMENT_SPEED = 0.4f;
    private TiledMap level;
    private MapRenderer renderer;
    private TileMovement tileMovement;

    private Tank tank;
    private Tree tree;

    public Level(String filename, Batch batch) {
        this.level = new TmxMapLoader().load(filename);
        this.renderer = createSingleLayerMapRenderer(this.level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(this.level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        tank = new Tank("images/tank_blue.png", 1, 1);
        tree = new Tree("images/greenTree.png", 1, 3);

        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    public void turnsTank() {
        tank.turnRight(tree);
        tank.turnLeft(tree);
        tank.turnUp(tree);
        tank.turnDown(tree);
    }

    public void renderTiles() {
        renderer.render();
    }

    public void renderObjects(Batch batch) {
        drawTextureRegionUnscaled(batch, tank.getPlayerGraphics(), tank.getPlayerRectangle(), tank.getPlayerRotation());
        drawTextureRegionUnscaled(batch, tree.getGraphics(), tree.getRectangle(), 0f);
    }

    public void calculateInterpolatedCoordinates(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(tank.getPlayerRectangle(), tank.getPlayerCoordinates(), tank.getPlayerDestinationCoordinates(), tank.getPlayerMovementProgress());

        tank.setPlayerMovementProgress(continueProgress(tank.getPlayerMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            tank.getPlayerCoordinates().set(tank.getPlayerDestinationCoordinates());
        }
    }

    public void dispose() {
        level.dispose();
        tank.dispose();
        tree.dispose();
    }
}
