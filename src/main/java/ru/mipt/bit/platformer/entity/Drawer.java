package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Drawer implements GraphicObject {
    private String imagePath;
    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;

    public Drawer(String imagePath) {
        this.imagePath = imagePath;
        draw();
    }

    public void draw() {
        this.texture = new Texture(this.imagePath);
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void dispose() {
        texture.dispose();
    }
}
