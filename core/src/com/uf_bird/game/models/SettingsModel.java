package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.uf_bird.game.controllers.SettingsController;
import com.uf_bird.game.utilities.SharedResources;

public class SettingsModel extends Model<SettingsController> {
    private final static float kViewportWidth = 150, kViewportHeight = 200;

    public final Stage stage;
    public final Color kBgColor;

    private final Sprite background;
    private final NinePatch board;
    private final CheckBox enableSoundCheckBox;
    private final CheckBox enableMusicCheckBox;
    private final ImageButton menuButton;

    public SettingsModel(SharedResources sr, final SettingsController settingsController) {
        super(sr, settingsController);

        ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight);
        stage = new Stage(viewport);
        stage.setDebugAll(sr.kDrawDebug);

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        background = gameTexturesSkin.getSprite("backGround.day");
        background.getTexture().getTextureData().prepare();

        Pixmap backgroundPixMap = background.getTexture().getTextureData().consumePixmap();
        kBgColor = new Color(backgroundPixMap.getPixel(background.getRegionX(), background.getRegionY()));
        backgroundPixMap.dispose();

        board = gameTexturesSkin.getPatch("board");

        enableSoundCheckBox = new CheckBox("Sound", gameTexturesSkin);
        enableSoundCheckBox.setChecked(sr.settings.soundEnabled());
        enableSoundCheckBox.addListener(controller.enableSoundCheckBoxTriggered());

        enableMusicCheckBox = new CheckBox("Music", gameTexturesSkin);
        enableMusicCheckBox.setChecked(sr.settings.musicEnabled());
        enableMusicCheckBox.addListener(controller.enableMusicCheckBoxTriggered());

        menuButton = new ImageButton(gameTexturesSkin, "menuButton");
        menuButton.addListener(controller.menuButtonPressed());
    }

    public Sprite getBackground() {
        return background;
    }

    public NinePatch getBoard() {
        return board;
    }

    public CheckBox getEnableSoundCheckBox() {
        return enableSoundCheckBox;
    }

    public CheckBox getEnableMusicCheckBox() {
        return enableMusicCheckBox;
    }

    public ImageButton getMenuButton() {
        return menuButton;
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
