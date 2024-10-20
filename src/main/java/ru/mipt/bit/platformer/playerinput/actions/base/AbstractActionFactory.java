package ru.mipt.bit.platformer.playerinput.actions.base;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;

public interface AbstractActionFactory {
    AbstractAction create(Level level, AbstractMovableLevelObject object);
}
