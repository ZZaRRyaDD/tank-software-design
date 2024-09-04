package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Tree {
    public Texture texture;
    public TextureRegion graphics;
    public GridPoint2 coordinates = new GridPoint2();
    public Rectangle rectangle = new Rectangle();

    public Tree(String imagePath, int coordinateX, int coordinateY) {
        this.texture = new Texture(imagePath);
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);

        // set player initial position
        this.coordinates = new GridPoint2(coordinateX, coordinateY);
    }

    public void dispose() {
        texture.dispose();
    }
}
