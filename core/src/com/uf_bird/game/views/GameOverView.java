package com.uf_bird.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uf_bird.game.controllers.GameOverController;
import com.uf_bird.game.models.GameOverModel;
import com.uf_bird.game.utilities.SharedResources;

public class GameOverView extends View<GameOverController, GameOverModel> {
    public GameOverView(SharedResources sr, GameOverController controller, GameOverModel model) {
        super(sr, controller, model);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().space(2);

        table.add(model.getGameOverLabel()).padBottom(12).row();
        table.add(model.getScoreTableGroup()).padBottom(6).row();
        table.add(model.getRestartButton());

        model.stage.addActor(table);
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
    public void render(float delta) {
        sr.spriteBatch.setProjectionMatrix(model.stage.getCamera().combined);
        sr.shapeRenderer.setProjectionMatrix(model.stage.getCamera().combined);
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
