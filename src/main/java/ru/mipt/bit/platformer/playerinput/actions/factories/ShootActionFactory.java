package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.base.Shootable;
import ru.mipt.bit.platformer.playerinput.actions.actions.ShootAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;

public class ShootActionFactory implements AbstractActionFactory<GameObject> {
    @Override
    public AbstractAction create(GameObject object) {
        return new ShootAction((Shootable) object);
    }
}
