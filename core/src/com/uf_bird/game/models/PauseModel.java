package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uf_bird.game.controllers.PauseController;
import com.uf_bird.game.utilities.SharedResources;

public class PauseModel extends Model<PauseController> {
    private final static float kViewportWidth = 150, kViewportHeight = 200;

    public final Stage stage;
    public final ImageButton resumeButton;
    public final ImageButton menuButton;
    private final NinePatch board;
    private final Label scoreLabel;

    public PauseModel(SharedResources sr, PauseController controller) {
        super(sr, controller);

        ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight);
        stage = new Stage(viewport, sr.spriteBatch);
        stage.setDebugAll(sr.kDrawDebug);

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        resumeButton = new ImageButton(gameTexturesSkin, "resumeButton");
        resumeButton.addListener(controller.resumeButtonPressed());
        resumeButton.pad(2);

        menuButton = new ImageButton(gameTexturesSkin, "menuButton");
        menuButton.addListener(controller.menuButtonPressed());
        menuButton.pad(2);

        scoreLabel = new Label("Score: " + controller.getGameModel().getScoreLabel().getText(), gameTexturesSkin);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setFontScale(0.3F);

        board = gameTexturesSkin.getPatch("board");
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    public ImageButton getResumeButton() {
        return resumeButton;
    }

    public ImageButton getMenuButton() {
        return menuButton;
    }

    public NinePatch getBoard() {
        return board;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    @Override
    public void dispose() {
    }
}
