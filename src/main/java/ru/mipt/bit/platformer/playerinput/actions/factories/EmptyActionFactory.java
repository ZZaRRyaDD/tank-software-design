package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.playerinput.actions.actions.EmptyAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;

public class EmptyActionFactory implements AbstractActionFactory<Tank> {
    @Override
    public AbstractAction create(Tank object) {
        return new EmptyAction();
    }
}
