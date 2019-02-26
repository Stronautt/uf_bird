package com.uf_bird.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.uf_bird.game.controllers.PauseController;
import com.uf_bird.game.models.PauseModel;
import com.uf_bird.game.utilities.SharedResources;

public class PauseView extends View<PauseController, PauseModel> {
    public PauseView(SharedResources sr, PauseController controller, PauseModel model) {
        super(sr, controller, model);

        Table outerTable = new Table();
        Table innerTable = new Table();
        outerTable.setFillParent(true);
        innerTable.defaults().space(2);

        innerTable.add(model.getScoreLabel()).colspan(2).padBottom(10).row();

        innerTable.add(model.getResumeButton());
        innerTable.add(model.getMenuButton());
        innerTable.setBackground(new NinePatchDrawable(model.getBoard()));
        innerTable.padBottom(8).padTop(5).padLeft(10).padRight(10);
        outerTable.add(innerTable);
        model.stage.addActor(outerTable);
    }

    @Override
    public void update(float delta) {
        controller.handleInput();
        model.update(delta);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(model.stage);
    }

    @Override
    public synchronized void render(float delta) {
        sr.spriteBatch.setProjectionMatrix(model.stage.getCamera().combined);
        sr.shapeRenderer.setProjectionMatrix(model.stage.getCamera().combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        sr.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        sr.shapeRenderer.setColor(0, 0, 0, 0.5F);
        sr.shapeRenderer.rect(0, 0, model.stage.getViewport().getWorldWidth(), model.stage.getViewport().getWorldHeight());
        sr.shapeRenderer.end();
        model.stage.draw();
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        model.stage.getViewport().update(width, height, true);
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
