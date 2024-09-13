package ru.mipt.bit.platformer.entity.tree;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.drawers.LevelObjectDrawer;

public class Tree {

    public LevelObjectDrawer drawer;
    private GridPoint2 coordinates;

    public Tree(String imagePath, int coordinateX, int coordinateY) {
        this.drawer = new LevelObjectDrawer(imagePath);
        this.drawer.draw();

        this.coordinates = new GridPoint2(coordinateX, coordinateY);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
