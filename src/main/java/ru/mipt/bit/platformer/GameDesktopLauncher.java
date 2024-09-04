package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.Tree;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Tank tank;
    private Tree tree;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        tank = new Tank("images/tank_blue.png", 1, 1);
        tree = new Tree("images/greenTree.png", 1, 3);

        moveRectangleAtTileCenter(groundLayer, tree.rectangle, tree.coordinates);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (isEqual(tank.playerMovementProgress, 1f)) {
                // check potential player destination for collision with obstacles
                if (!tree.coordinates.equals(incrementedY(tank.playerCoordinates))) {
                    tank.playerDestinationCoordinates.y++;
                    tank.playerMovementProgress = 0f;
                }
                tank.playerRotation = 90f;
            }
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (isEqual(tank.playerMovementProgress, 1f)) {
                if (!tree.coordinates.equals(decrementedX(tank.playerCoordinates))) {
                    tank.playerDestinationCoordinates.x--;
                    tank.playerMovementProgress = 0f;
                }
                tank.playerRotation = -180f;
            }
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (isEqual(tank.playerMovementProgress, 1f)) {
                if (!tree.coordinates.equals(decrementedY(tank.playerCoordinates))) {
                    tank.playerDestinationCoordinates.y--;
                    tank.playerMovementProgress = 0f;
                }
                tank.playerRotation = -90f;
            }
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (isEqual(tank.playerMovementProgress, 1f)) {
                if (!tree.coordinates.equals(incrementedX(tank.playerCoordinates))) {
                    tank.playerDestinationCoordinates.x++;
                    tank.playerMovementProgress = 0f;
                }
                tank.playerRotation = 0f;
            }
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(
                tank.playerRectangle,
                tank.playerCoordinates,
                tank.playerDestinationCoordinates,
                tank.playerMovementProgress
        );

        tank.playerMovementProgress = continueProgress(tank.playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(tank.playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            tank.playerCoordinates.set(tank.playerDestinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, tank.playerGraphics, tank.playerRectangle, tank.playerRotation);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.graphics, tree.rectangle, 0f);

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        tree.dispose();
        tank.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
