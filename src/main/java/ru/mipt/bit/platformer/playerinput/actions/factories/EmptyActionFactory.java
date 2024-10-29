package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.actions.EmptyAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;

public class EmptyActionFactory implements AbstractActionFactory {
    @Override
    public AbstractAction create(AbstractMovableLevelObject object) {
        return new EmptyAction();
    }
}
