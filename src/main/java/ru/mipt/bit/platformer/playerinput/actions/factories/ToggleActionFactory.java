package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.draw.decorators.base.Toggle;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.playerinput.actions.actions.ToggleAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;


public class ToggleActionFactory implements AbstractActionFactory<Tank> {
    private final LevelGraphic levelGraphic;

    public ToggleActionFactory(LevelGraphic levelGraphic) {
        this.levelGraphic = levelGraphic;
    }

    @Override
    public AbstractAction create(Tank object) {
        return new ToggleAction((Toggle) levelGraphic);
    }
}
