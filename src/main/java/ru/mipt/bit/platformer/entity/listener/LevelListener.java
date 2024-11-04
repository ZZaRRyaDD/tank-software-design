package ru.mipt.bit.platformer.entity.listener;

import ru.mipt.bit.platformer.entity.objects.base.GameObject;

public interface LevelListener {
    void onAddGameObject(GameObject object);
    void onDeleteGameObject(GameObject object);
}
