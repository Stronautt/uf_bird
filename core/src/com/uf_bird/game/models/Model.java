package com.uf_bird.game.models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.utilities.SharedResources;

public abstract class Model<ControllerType extends Controller> extends Actor {
    protected final SharedResources sr;
    protected final ControllerType controller;

    public Model(SharedResources sr, ControllerType controller) {
        this.sr = sr;
        this.controller = controller;
    }

    public abstract void update(float delta);

    public abstract void dispose();
}
