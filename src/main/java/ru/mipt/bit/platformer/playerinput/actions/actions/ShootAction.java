package ru.mipt.bit.platformer.playerinput.actions.actions;

import ru.mipt.bit.platformer.entity.objects.base.Shootable;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public class ShootAction implements AbstractAction {
    private final Shootable object;

    public ShootAction(Shootable object) {
        this.object = object;
    }

    public void apply() {
        object.shoot();
    }
}
