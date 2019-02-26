package com.uf_bird.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uf_bird.game.models.GameModel;
import com.uf_bird.game.models.PauseModel;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.PauseView;

public class PauseController extends Controller<PauseModel, PauseView> {
    GameModel gameModel;

    public PauseController(SharedResources sr, GameModel gameModel) {
        super(sr);
        this.gameModel = gameModel;
        model = new PauseModel(sr, this);
        setScreen(new PauseView(sr, this, model));
    }

    public ChangeListener resumeButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.isPaused(false);
                sr.vcm.pop();
            }
        };
    }

    public ChangeListener menuButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.play("swooshing");
                sr.vcm.set(new MenuController(sr));
            }
        };
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void create() {
    }
}
