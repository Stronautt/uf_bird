package com.uf_bird.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uf_bird.game.models.GameOverModel;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.GameOverView;

public class GameOverController extends Controller<GameOverModel, GameOverView> {
    public GameOverController(SharedResources sr, int finalScore) {
        super(sr);
        model = new GameOverModel(sr, this, finalScore);
        setScreen(new GameOverView(sr, this, model));
    }

    public ChangeListener restartButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.play("swooshing");
                sr.vcm.set(new GameController(sr));
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
