package ru.mipt.bit.platformer.entity.drawers.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.entity.listener.LevelListener;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.Map;

public interface LevelGraphic extends LevelListener {
    void render();
    void dispose();
    Batch getBatch();
    Map<GameObject, GameObjectGraphic> getGraphicObjects();
    void addStrategyGraphics(Class<? extends GameObject> clazz, GraphicFactory graphicsFactory);
}
