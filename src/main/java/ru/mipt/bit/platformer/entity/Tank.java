package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Tank {
    public Texture texture;
    public TextureRegion playerGraphics;
    public Rectangle playerRectangle;
    public GridPoint2 playerCoordinates;
    public GridPoint2 playerDestinationCoordinates;
    public float playerMovementProgress = 1f;
    public float playerRotation;

    public Tank(String imagePath, int coordinateX, int coordinateY) {
        this.texture = new Texture(imagePath);
        this.playerGraphics = new TextureRegion(this.texture);
        this.playerRectangle = createBoundingRectangle(this.playerGraphics);

        // set player initial position
        this.playerDestinationCoordinates = new GridPoint2(coordinateX, coordinateY);
        this.playerCoordinates = new GridPoint2(this.playerDestinationCoordinates);
        this.playerRotation = 0f;
    }

    public void dispose() {
        texture.dispose();
    }
}
