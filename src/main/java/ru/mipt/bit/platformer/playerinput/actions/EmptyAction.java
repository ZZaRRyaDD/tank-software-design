package ru.mipt.bit.platformer.playerinput.actions;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public class EmptyAction implements AbstractAction {
    public void apply(Level level, AbstractMovableLevelObject object) {}
}
