package ru.mipt.bit.platformer.entity.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.drawers.LevelDrawer;
import ru.mipt.bit.platformer.entity.tree.Tree;
import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.tank.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level {
    private static final float MOVEMENT_SPEED = 0.4f;

    public LevelDrawer drawer;
    private Tank tank;
    private List<Tree> trees  = new ArrayList<Tree>();

    public Level(String filename, Batch batch) {
        this.drawer = new LevelDrawer(filename, batch, this);
        this.drawer.draw();

        this.addTank();
        this.addTrees();
    }

    public void addTank() {
        tank = new Tank("images/tank_blue.png", 1, 1);
    }

    public void addTrees() {
        Tree tree1 = new Tree("images/greenTree.png", 1, 3);

        trees.add(tree1);

        for (Tree tree : trees) {
            moveRectangleAtTileCenter(this.drawer.getGroundLayer(), tree.drawer.getRectangle(), tree.getCoordinates());
        }
    }

    public void moveTank(Direction direction) {
        if (direction != Direction.NULL) {
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

    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }

//    public void renderObjects(Batch batch) {
//        drawTextureRegionUnscaled(batch, tank.drawer.getGraphics(), tank.drawer.getRectangle(), tank.getRotation());
//        for (Tree tree : trees) {
//            drawTextureRegionUnscaled(batch, tree.drawer.getGraphics(), tree.drawer.getRectangle(), 0f);
//        }
//    }

//    public void renderMoves(float deltaTime) {
//        drawer.getTileMovement().moveRectangleBetweenTileCenters(tank.drawer.getRectangle(), tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
//
//        tank.setMovementProgress(continueProgress(tank.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
//        if (isEqual(tank.getMovementProgress(), 1f)) {
//            tank.setCoordinates(tank.getDestinationCoordinates());
//        }
//
//        drawer.renderTiles();
//    }

    public Tank getTank() {
        return tank;
    }

    public List<Tree> getTress() {
        return trees;
    }
}
