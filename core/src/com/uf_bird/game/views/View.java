package com.uf_bird.game.views;

import com.badlogic.gdx.Screen;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.models.Model;
import com.uf_bird.game.utilities.SharedResources;

public abstract class View<ControllerType extends Controller, ModelType extends Model> implements Screen {
    protected final SharedResources sr;
    protected final ControllerType controller;
    protected final ModelType model;

    public View(SharedResources sr, ControllerType controller, ModelType model) {
        this.sr = sr;
        this.controller = controller;
        this.model = model;
    }

    public abstract void update(float delta);
}
