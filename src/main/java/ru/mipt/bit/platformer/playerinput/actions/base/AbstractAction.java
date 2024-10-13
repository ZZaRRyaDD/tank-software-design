package ru.mipt.bit.platformer.playerinput.actions.base;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;

public interface AbstractAction {
    void apply(Level level, AbstractMovableLevelObject object);
}
