package ru.mipt.bit.platformer.entity.drawers.drawers.factories;

import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.drawers.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.BulletDrawer;
import ru.mipt.bit.platformer.entity.objects.Bullet;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletDrawerFactory implements GraphicFactory {
    private final String imagePath;
    private final TileMovement tileMovement;

    public BulletDrawerFactory(String imagePath, TileMovement tileMovement) {
        this.imagePath = imagePath;
        this.tileMovement = tileMovement;
    }

    @Override
    public GameObjectGraphic create(GameObject object) {
        return new BulletDrawer(imagePath, (Bullet) object, tileMovement);
    }
}
