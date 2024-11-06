package ru.mipt.bit.platformer.entity.drawers.drawers.factories;

import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.drawers.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.TankDrawer;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankDrawerFactory implements GraphicFactory {
    private final String imagePath;
    private final TileMovement tileMovement;

    public TankDrawerFactory(String imagePath, TileMovement tileMovement) {
        this.imagePath = imagePath;
        this.tileMovement = tileMovement;
    }

    @Override
    public GameObjectGraphic create(GameObject object) {
        return new TankDrawer(imagePath, (Tank) object, tileMovement);
    }
}
