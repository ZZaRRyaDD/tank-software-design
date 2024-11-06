package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.listener.LevelListener;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final Integer height;
    private final Integer width;
    private final List<LevelListener> listeners;

    public Level(Integer height, Integer width, List<LevelListener> listeners) {
        this.height = height;
        this.width = width;
        this.listeners = listeners;
    }

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
        listeners.forEach(listener -> listener.onAddGameObject(object));
    }

    public void deleteGameObject(GameObject object) {
        gameObjects.remove(object);
        listeners.forEach(listener -> listener.onDeleteGameObject(object));
    }

    public boolean isFreePoint(GridPoint2 point) {
        for (GameObject object : gameObjects) {
            if (object.isBusyCoordinate(point)) {
                return false;
            }
        }
        return true;
    }

    public GameObject getGameObjectByCoordinates(GridPoint2 point) {
        for (GameObject object : gameObjects) {
            if (object.getCoordinates().equals(point)) {
                return object;
            }
        }
        return null;
    }

    public boolean intoMapBorder(GridPoint2 point) {
        return (0 <= point.x && point.x < width) && (0 <= point.y && point.y < height);
    }

    public boolean hasObject(GameObject object) {
        return gameObjects.contains(object);
    }

    public void updateState(float deltaTime) {
        List<GameObject> copyGameObjects = new ArrayList<>(gameObjects);
        for (GameObject object : copyGameObjects) {
            object.updateState(deltaTime);
        }
    }
}