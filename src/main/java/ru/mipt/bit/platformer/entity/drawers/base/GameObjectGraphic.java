package ru.mipt.bit.platformer.entity.drawers.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface GameObjectGraphic {
    void dispose();
    void draw(Batch batch);
    Rectangle getRectangle();
}
