package com.uf_bird.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uf_bird.game.controllers.MenuController;
import com.uf_bird.game.models.MenuModel;
import com.uf_bird.game.utilities.SharedResources;

public class MenuView extends View<MenuController, MenuModel> {
    public MenuView(SharedResources sr, MenuController controller, MenuModel model) {
        super(sr, controller, model);

        Table table = new Table();
        table.defaults().space(6);
        table.setFillParent(true);
        table.add(model.getGameName()).colspan(2).row();
        table.add(model.getBird()).pad(12, 0, 20, 0).colspan(2).row();
        table.add(model.getPlayButton());
        table.add(model.getSettingsButton());
        model.stage.addActor(table);
    }

    @Override
    public synchronized void update(float delta) {
        controller.handleInput();
        model.update(delta);
    }

    @Override
    public void show() {
        if (sr.settings.musicEnabled() && !sr.musicMixer.isPlaying("ost")) {
            sr.musicMixer.play("ost");
        }
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
