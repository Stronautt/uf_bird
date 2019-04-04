package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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
    private final Table dayTimeSelectBox;
    private final ImageButton menuButton;

    public SettingsModel(SharedResources sr, final SettingsController settingsController) {
        super(sr, settingsController);

        ExtendViewport viewport = new ExtendViewport(kViewportWidth, kViewportHeight);
        stage = new Stage(viewport);
        stage.setDebugAll(sr.kDrawDebug);

        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        background = gameTexturesSkin.getSprite("backGround." + sr.settings.dayTime());
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

        dayTimeSelectBox = new Table();
        SelectBox<String> dayTimeSelect = new SelectBox<String>(gameTexturesSkin);
        dayTimeSelect.getStyle().font.getData().setScale(0.2F);
        dayTimeSelect.setAlignment(Align.center);
        dayTimeSelect.getList().setAlignment(Align.center);
        dayTimeSelect.setItems("Day", "Night", "Dynamic");
        dayTimeSelect.setSelected(sr.settings.dayTimeRaw().substring(0, 1).toUpperCase() + sr.settings.dayTimeRaw().substring(1));
        dayTimeSelect.getStyle().listStyle.selection.setRightWidth(3);
        dayTimeSelect.getStyle().listStyle.selection.setLeftWidth(3);
        dayTimeSelect.addListener(controller.dayTimeSelectBoxTriggered());
        Label dayTimeLabel = new Label("Day Time", gameTexturesSkin);
        dayTimeLabel.setFontScale(0.29F);
        dayTimeSelectBox.add(dayTimeLabel).padRight(10);
        dayTimeSelectBox.add(dayTimeSelect);

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

    public Table getDayTimeSelectBox() {
        return dayTimeSelectBox;
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
