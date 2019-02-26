package com.uf_bird.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uf_bird.game.models.GameModel;
import com.uf_bird.game.utilities.SharedResources;

public class GameStage extends Stage {
    private final SharedResources sr;
    private final GameModel gameModel;

    public GameStage(SharedResources sr, Viewport viewport, GameModel gameModel) {
        super(viewport, sr.spriteBatch);
        this.sr = sr;
        this.gameModel = gameModel;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!super.touchDown(screenX, screenY, pointer, button)) {
            gameModel.getBird().jump();
            return false;
        } else {
            return true;
        }
    }
}
