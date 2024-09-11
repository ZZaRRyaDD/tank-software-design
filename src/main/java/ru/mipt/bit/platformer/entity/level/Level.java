package ru.mipt.bit.platformer.entity.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.tree.Tree;
import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.tank.TankDirection;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level {
    private static final float MOVEMENT_SPEED = 0.4f;
    private TiledMap level;
    private MapRenderer renderer;
    private TileMovement tileMovement;

    private Tank tank;
    private List<Tree> trees  = new ArrayList<Tree>();

    public Level(String filename, Batch batch) {
        this.level = new TmxMapLoader().load(filename);
        this.renderer = createSingleLayerMapRenderer(this.level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(this.level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.addTank();
        this.addTrees(groundLayer);
    }

    public void addTank() {
        tank = new Tank("images/tank_blue.png", 1, 1);
    }

    public void addTrees(TiledMapTileLayer groundLayer) {
        Tree tree1 = new Tree("images/greenTree.png", 1, 3);

        trees.add(tree1);

        for (Tree tree : trees) {
            moveRectangleAtTileCenter(groundLayer, tree.drawer.getRectangle(), tree.getCoordinates());
        }
    }

    public void moveTank(TankDirection direction) {
        if (direction != TankDirection.NULL) {
            GridPoint2 point = new GridPoint2(tank.getCoordinates());
            point = point.add(direction.directionPoint.x, direction.directionPoint.y);
            if (!inPointIsTree(point) && isEqual(tank.getMovementProgress(), 1f)) {
                tank.move(direction.directionPoint, direction.directionRotation);
            }
        }
    }

    public boolean inPointIsTree(GridPoint2 point) {
        for (Tree tree : trees) {
            if (tree.getCoordinates().equals(point)) {
                return true;
            }
        }
        return false;
    }

    public void renderTiles() {
        renderer.render();
    }

    public void renderObjects(Batch batch) {
        drawTextureRegionUnscaled(batch, tank.drawer.getGraphics(), tank.drawer.getRectangle(), tank.getRotation());
        for (Tree tree : trees) {
            drawTextureRegionUnscaled(batch, tree.drawer.getGraphics(), tree.drawer.getRectangle(), 0f);
        }
    }

    public void renderMoves(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(tank.drawer.getRectangle(), tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());

        tank.setMovementProgress(continueProgress(tank.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getMovementProgress(), 1f)) {
            tank.setCoordinates(tank.getDestinationCoordinates());
        }

        renderTiles();
    }

    public void dispose() {
        level.dispose();
        tank.drawer.dispose();
        for (Tree tree : trees) {
            tree.drawer.dispose();
        }
    }
}
