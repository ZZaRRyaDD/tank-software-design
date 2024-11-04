package ru.mipt.bit.platformer.entity.drawers.base;

import ru.mipt.bit.platformer.entity.objects.base.GameObject;

public interface GraphicFactory {
    GameObjectGraphic create(GameObject object);
}
