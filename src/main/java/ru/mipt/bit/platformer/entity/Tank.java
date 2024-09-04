package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import ru.mipt.bit.platformer.entity.Tree;

public class Tank {
    private Texture texture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    private GridPoint2 playerCoordinates;
    private GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private float playerRotation;

    public Tank(String imagePath, int coordinateX, int coordinateY) {
        this.texture = new Texture(imagePath);
        this.playerGraphics = new TextureRegion(this.texture);
        this.playerRectangle = createBoundingRectangle(this.playerGraphics);

        // set player initial position
        this.playerDestinationCoordinates = new GridPoint2(coordinateX, coordinateY);
        this.playerCoordinates = new GridPoint2(this.playerDestinationCoordinates);
        this.playerRotation = 0f;
    }

    public TextureRegion getPlayerGraphics() {
        return playerGraphics;
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public float getPlayerRotation() {
        return playerRotation;
    }

    public void setPlayerMovementProgress(float playerMovementProgress) {
        this.playerMovementProgress = playerMovementProgress;
    }

    public void turnRight(Tree tree) {
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!tree.getCoordinates().equals(incrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 0f;
            }
        }
    }

    public void turnLeft(Tree tree) {
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!tree.getCoordinates().equals(decrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -180f;
            }
        }
    }

    public void turnUp(Tree tree) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (isEqual(playerMovementProgress, 1f)) {
                // check potential player destination for collision with obstacles
                if (!tree.getCoordinates().equals(incrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 90f;
            }
        }
    }

    public void turnDown(Tree tree) {
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!tree.getCoordinates().equals(decrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -90f;
            }
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
