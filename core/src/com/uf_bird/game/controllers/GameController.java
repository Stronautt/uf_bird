package com.uf_bird.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uf_bird.game.models.GameModel;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.GameView;

public class GameController extends Controller<GameModel, GameView> {
    public GameController(SharedResources sr) {
        super(sr);
        model = new GameModel(sr, this);
        setScreen(new GameView(sr, this, model));
    }

    public ChangeListener pauseButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.isPaused(true);
                sr.vcm.push(new PauseController(sr, model));
            }
        };
    }

    public ChangeListener playInvitationButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.getBird().goForward();
                model.uiStage.clear();
                model.uiStage.addActor(model.uiTable);
                model.getBird().clearActions();
            }
        };
    }

    public void gameOver() {
        model.isPaused(true);
        model.uiStage.clear();
        sr.vcm.push(new GameOverController(sr, model.getScore()));
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void create() {
    }
}
