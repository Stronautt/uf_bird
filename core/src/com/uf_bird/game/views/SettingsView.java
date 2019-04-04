package com.uf_bird.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.uf_bird.game.controllers.SettingsController;
import com.uf_bird.game.models.SettingsModel;
import com.uf_bird.game.utilities.SharedResources;

public class SettingsView extends View<SettingsController, SettingsModel> {
    public SettingsView(SharedResources sr, SettingsController controller, SettingsModel model) {
        super(sr, controller, model);

        Table outerTable = new Table();
        Table innerTable = new Table();
        outerTable.setFillParent(true);
        innerTable.defaults().space(6);

        CheckBox enableSoundCheckBox = model.getEnableSoundCheckBox();
        enableSoundCheckBox.getLabelCell().padLeft(20);
        enableSoundCheckBox.getLabel().setFontScale(0.29F);
        innerTable.add(enableSoundCheckBox).row();

        CheckBox enableMusicCheckBox = model.getEnableMusicCheckBox();
        enableMusicCheckBox.getLabelCell().padLeft(20);
        enableMusicCheckBox.getLabel().setFontScale(0.29F);
        innerTable.add(enableMusicCheckBox).padTop(-5).row();

        innerTable.add(model.getDayTimeSelectBox()).row();

        innerTable.add(model.getMenuButton());
        innerTable.setBackground(new NinePatchDrawable(model.getBoard()));
        innerTable.padBottom(8).padTop(5).padLeft(10).padRight(10);
        outerTable.add(innerTable);
        model.stage.addActor(outerTable);
    }

    @Override
    public synchronized void update(float delta) {
        controller.handleInput();
        model.update(delta);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(model.stage);
        Gdx.gl.glClearColor(model.kBgColor.r, model.kBgColor.g, model.kBgColor.b, model.kBgColor.a);
    }

    @Override
    public synchronized void render(float delta) {
        sr.spriteBatch.setProjectionMatrix(model.stage.getCamera().combined);
        sr.spriteBatch.begin();

        // Draw background
        for (int i = 0; i <= model.stage.getCamera().viewportWidth / model.getBackground().getWidth(); i++)
            sr.spriteBatch.draw(model.getBackground(), i * model.getBackground().getWidth(), 0);

        sr.spriteBatch.end();
        model.stage.draw();
        update(delta);
    }

    @Override
    public void resize(int w, int h) {
        model.stage.getViewport().update(w, h, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
