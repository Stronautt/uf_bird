package com.uf_bird.game.controllers;

import com.badlogic.gdx.Game;
import com.uf_bird.game.models.Model;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.View;

public abstract class Controller<ModelType extends Model, ViewType extends View> extends Game {
    protected final SharedResources sr;
    protected ModelType model;

    Controller(SharedResources sr) {
        this.sr = sr;
    }

    public abstract void handleInput();

    @SuppressWarnings("unchecked")
    public ViewType getView() {
        if (screen instanceof View<?, ?>) {
            return (ViewType) screen;
        }
        return null;
    }

    @Override
    public void dispose() {
        super.dispose();
        model.dispose();
        getView().dispose();
    }
}
