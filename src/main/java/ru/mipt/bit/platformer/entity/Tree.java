package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Tree {
    private Texture texture;
    private TextureRegion graphics;
    private GridPoint2 coordinates = new GridPoint2();
    private Rectangle rectangle = new Rectangle();

    public Tree(String imagePath, int coordinateX, int coordinateY) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);

        // set player initial position
        this.coordinates = new GridPoint2(coordinateX, coordinateY);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void dispose() {
        texture.dispose();
    }
}
