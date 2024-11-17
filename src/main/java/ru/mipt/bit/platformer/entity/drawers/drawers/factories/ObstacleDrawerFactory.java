package ru.mipt.bit.platformer.entity.drawers.drawers.factories;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.drawers.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.ObstacleDrawer;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

public class ObstacleDrawerFactory implements GraphicFactory {
    private final String imagePath;
    private final TiledMapTileLayer groundLayer;

    public ObstacleDrawerFactory(String imagePath, TiledMapTileLayer groundLayer) {
        this.imagePath = imagePath;
        this.groundLayer = groundLayer;
    }

    @Override
    public GameObjectGraphic create(GameObject object) {
        return new ObstacleDrawer(imagePath, (Obstacle) object, groundLayer);
    }
}
