package com.uf_bird.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uf_bird.game.controllers.GameController;
import com.uf_bird.game.controllers.GameStage;
import com.uf_bird.game.utilities.SharedResources;

public class GameModel extends Model<GameController> {
    private final static float kViewportWidth = 120, kViewportHeight = 200;
    public final static Vector2 kGravity = new Vector2(0, -4.8F);
    public final static float kBirdXOffset = 0.15F;

    public final Color kBgColor;
    public final OrthographicCamera camera = new OrthographicCamera();
    public final ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight, camera);
    public final GameStage uiStage = new GameStage(sr, new ExtendViewport(kViewportWidth, kViewportHeight), this);
    public final Table invitationTable = new Table();
    public final Table uiTable = new Table();

    private final Sprite background;
    private final Bird bird;
    private final TubeGates tubeGates;
    private final Ground ground;
    private final Label fpsLabel;
    private final Label scoreLabel;
    private final ImageButton pauseButton;
    private final ImageButton playInvitationButton;
    private final Image getReadyLabel;

    private int score;
    private boolean paused;

    public GameModel(SharedResources sr, GameController gameController) {
        super(sr, gameController);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        background = gameTexturesSkin.getSprite("backGround.day");
        background.getTexture().getTextureData().prepare();

        Pixmap backgroundPixMap = background.getTexture().getTextureData().consumePixmap();
        kBgColor = new Color(backgroundPixMap.getPixel(background.getRegionX(), background.getRegionY()));
        backgroundPixMap.dispose();

        bird = new Bird(sr, gameController);
        bird.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, -3, 0.35f), Actions.moveBy(0, 3, 0.35f))));

        tubeGates = new TubeGates(sr, gameController, viewport.getWorldWidth(), 0);
        tubeGates.setGroundLevel(Ground.kHeight);

        ground = new Ground(sr, gameController, -viewport.getWorldWidth() / 2.0F, 0);

        fpsLabel = new Label("0", sr.assetManager.get(sr.kDefaultSkin, Skin.class));
        fpsLabel.setFontScale(0.15F);

        scoreLabel = new Label("0", sr.assetManager.get(sr.kDefaultSkin, Skin.class), "shadowed");
        scoreLabel.setFontScale(0.5F);
        scoreLabel.setAlignment(Align.center);

        pauseButton = new ImageButton(gameTexturesSkin, "pauseButton");
        pauseButton.addListener(controller.pauseButtonPressed());
        pauseButton.pad(3);

        playInvitationButton = new ImageButton(gameTexturesSkin, "playInvitationButton");
        playInvitationButton.align(Align.top);
        playInvitationButton.padTop(20);
        playInvitationButton.addListener(controller.playInvitationButtonPressed());

        getReadyLabel = new Image(gameTexturesSkin.get("getReadyLabel", Sprite.class));

        camera.setToOrtho(false);
        camera.position.set(0, 0, 0);
        worldChanged();

        uiStage.setDebugAll(sr.kDrawDebug);
    }

    public void worldChanged() {
        tubeGates.setWorldParams(viewport.getWorldWidth(), viewport.getWorldHeight());
        ground.setWorldWidth(viewport.getWorldWidth());
        ground.setPosition(ground.getX(), -viewport.getWorldHeight() / 2.0F + Ground.kHeight);
        camera.position.x = viewport.getWorldWidth() * (1 - 2 * kBirdXOffset) / 2 + bird.getX();
    }

    public boolean isPaused() {
        return paused;
    }

    public void isPaused(boolean value) {
        paused = value;
        if (!paused) {
            Gdx.input.setInputProcessor(uiStage);
        }
    }

    @Override
    public synchronized void update(float delta) {
        if (isPaused()) {
            camera.update();
            return;
        }
        uiStage.act(delta);

        bird.update(delta);

        float worldX = camera.position.x - viewport.getWorldWidth() / 2.0F;
        tubeGates.update(delta, worldX);
        ground.update(delta, worldX);

        fpsLabel.setText(Gdx.graphics.getFramesPerSecond());

        camera.position.x = viewport.getWorldWidth() * (1 - 2 * kBirdXOffset) / 2 + bird.getX();
        camera.update();

        if (bird.isAlive() && bird.collides(tubeGates)) {
            sr.musicMixer.play("hit");
            bird.die();
        } else if (bird.collides(ground)) {
            if (bird.isAlive()) {
                sr.musicMixer.play("hit");
            }
            controller.gameOver();
        } else if (tubeGates.passedGate(bird)) {
            sr.musicMixer.play("point");
            score++;
            scoreLabel.setText(score);
        }
    }

    public Sprite getBackground() {
        return background;
    }

    public Bird getBird() {
        return bird;
    }

    public TubeGates getTubeGates() {
        return tubeGates;
    }

    public Ground getGround() {
        return ground;
    }

    public Label getFpsLabel() {
        return fpsLabel;
    }

    public int getScore() {
        return score;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public ImageButton getPlayInvitationButton() {
        return playInvitationButton;
    }

    public Image getGetReadyLabel() {
        return getReadyLabel;
    }

    @Override
    public void dispose() {
    }
}
