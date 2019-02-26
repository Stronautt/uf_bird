package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uf_bird.game.controllers.GameOverController;
import com.uf_bird.game.utilities.SharedResources;

public class GameOverModel extends Model<GameOverController> {
    private final static float kViewportWidth = 150, kViewportHeight = 200;

    public final Stage stage;

    private final Image gameOverLabel;
    private final Group scoreTableGroup;
    private final ImageButton restartButton;

    public GameOverModel(SharedResources sr, GameOverController controller, int finalScore) {
        super(sr, controller);

        ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight);
        stage = new Stage(viewport, sr.spriteBatch);
        stage.setDebugAll(sr.kDrawDebug);

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        gameOverLabel = new Image(gameTexturesSkin.get("gameOverLabel", Sprite.class));
        gameOverLabel.addAction(Actions.moveBy(0, -5, 0.3F));

        scoreTableGroup = new Group();

        Image scoreTable = new Image(gameTexturesSkin.get("scoreTable", Sprite.class));
        scoreTableGroup.addActor(scoreTable);

        if (finalScore > 5) {
            Image medal = new Image(gameTexturesSkin.get("medal." + (int) sr.clamp(finalScore / 5.0F - 1, 0, 3), Sprite.class));
            medal.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 1, 0.7f), Actions.moveBy(0, -1, 0.7f))));
            medal.setPosition(13, 14);
            scoreTableGroup.addActor(medal);
        }

        Label score = new Label(finalScore + "", gameTexturesSkin);
        score.setAlignment(Align.right);
        score.setFontScale(0.18F);
        score.setWidth(65);
        score.setHeight(score.getPrefHeight());
        score.setPosition(36, 33);
        scoreTableGroup.addActor(score);

        if (sr.settings.gameMaxScore() < finalScore) {
            sr.settings.gameMaxScore(finalScore);
        }
        Label maxScore = new Label(sr.settings.gameMaxScore() + "", gameTexturesSkin);
        maxScore.setAlignment(Align.right);
        maxScore.setFontScale(0.18F);
        maxScore.setWidth(65);
        maxScore.setHeight(score.getPrefHeight());
        maxScore.setPosition(36, 12);
        scoreTableGroup.addActor(maxScore);

        scoreTableGroup.setHeight(scoreTable.getHeight());
        scoreTableGroup.setWidth(scoreTable.getWidth());
        scoreTableGroup.getColor().a = 0;
        scoreTableGroup.addAction(Actions.fadeIn(0.5F));

        restartButton = new ImageButton(gameTexturesSkin, "playButton");
        restartButton.addListener(controller.restartButtonPressed());
        restartButton.addAction(Actions.moveBy(0, 5, 0.3F));
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    public Image getGameOverLabel() {
        return gameOverLabel;
    }

    public Group getScoreTableGroup() {
        return scoreTableGroup;
    }

    public ImageButton getRestartButton() {
        return restartButton;
    }

    @Override
    public void dispose() {
    }
}
