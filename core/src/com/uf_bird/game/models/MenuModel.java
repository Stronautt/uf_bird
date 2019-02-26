package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uf_bird.game.UfBird;
import com.uf_bird.game.controllers.MenuController;
import com.uf_bird.game.utilities.SharedResources;

public class MenuModel extends Model<MenuController> {
    private final static float kViewportWidth = 150, kViewportHeight = 200;

    public final Color kBgColor;
    public final Stage stage;

    private final Sprite background;
    private final ImageButton playButton, settingsButton;
    private final Label gameName;
    private final Bird bird;

    public MenuModel(SharedResources sr, final MenuController controller) {
        super(sr, controller);
        ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight);
        stage = new Stage(viewport, sr.spriteBatch);
        stage.setDebugAll(sr.kDrawDebug);

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        background = gameTexturesSkin.getSprite("backGround.day");
        background.getTexture().getTextureData().prepare();

        Pixmap backgroundPixMap = background.getTexture().getTextureData().consumePixmap();
        kBgColor = new Color(backgroundPixMap.getPixel(background.getRegionX(), background.getRegionY()));
        backgroundPixMap.dispose();

        gameName = new Label(UfBird.class.getSimpleName(), gameTexturesSkin, "shadowed");

        bird = new Bird(sr, controller);
        bird.setScale(1.7F);
        bird.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, -3, 0.35f), Actions.moveBy(0, 3, 0.35f))));

        playButton = new ImageButton(gameTexturesSkin, "playButton");
        playButton.addListener(controller.playButtonPressed());

        settingsButton = new ImageButton(gameTexturesSkin, "settingsButton");
        settingsButton.addListener(controller.settingsButtonPressed());
    }

    public Sprite getBackground() {
        return background;
    }

    public ImageButton getPlayButton() {
        return playButton;
    }

    public ImageButton getSettingsButton() {
        return settingsButton;
    }

    public Label getGameName() {
        return gameName;
    }

    public Bird getBird() {
        return bird;
    }

    @Override
    public synchronized void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
