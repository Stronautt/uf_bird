package com.uf_bird.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uf_bird.game.models.MenuModel;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.MenuView;

public class MenuController extends Controller<MenuModel, MenuView> {
    public MenuController(SharedResources sr) {
        super(sr);
        model = new MenuModel(sr, this);
        setScreen(new MenuView(sr, this, model));
    }

    public ChangeListener playButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.play("swooshing");
                sr.musicMixer.stop("ost");
                sr.vcm.set(new GameController(sr));
            }
        };
    }

    public ChangeListener settingsButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.play("swooshing");
                sr.vcm.set(new SettingsController(sr));
            }
        };
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void create() {
    }
}
