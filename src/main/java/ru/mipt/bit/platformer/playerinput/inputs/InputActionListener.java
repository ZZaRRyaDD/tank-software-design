package ru.mipt.bit.platformer.playerinput.inputs;

import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public interface InputActionListener {
    AbstractAction getAction(AbstractMovableLevelObject object);
}
