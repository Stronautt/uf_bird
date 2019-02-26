package com.uf_bird.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.uf_bird.game.controllers.GameController;
import com.uf_bird.game.models.GameModel;
import com.uf_bird.game.utilities.SharedResources;

public class GameView extends View<GameController, GameModel> {
    public GameView(SharedResources sr, GameController controller, GameModel model) {
        super(sr, controller, model);

        model.invitationTable.setFillParent(true);
        model.uiTable.setFillParent(true);

        model.invitationTable.add(model.getGetReadyLabel()).padTop(20).row();
        model.invitationTable.add(model.getPlayInvitationButton()).padTop(15).grow();

        if (sr.kDrawDebug) {
            model.uiTable.add(model.getFpsLabel()).width(Value.percentWidth(.333F, model.uiTable)).expandY().top().left();
        } else {
            model.uiTable.add().width(Value.percentWidth(.333F, model.uiTable));
        }
        model.uiTable.add(model.getScoreLabel()).width(Value.percentWidth(.333F, model.uiTable)).expandY().top().padTop(20);
        model.uiTable.add(model.getPauseButton()).expand().top().right();

        model.uiStage.addActor(model.invitationTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(model.uiStage);
        Gdx.gl.glClearColor(model.kBgColor.r, model.kBgColor.g, model.kBgColor.b, model.kBgColor.a);
    }

    @Override
    public synchronized void update(float delta) {
        controller.handleInput();
        model.update(delta);
    }

    @Override
    public synchronized void render(float delta) {
        if (model.isPaused()) {
            delta = 0;
        }

        sr.spriteBatch.setProjectionMatrix(model.camera.combined);
        sr.shapeRenderer.setProjectionMatrix(model.camera.combined);
        sr.spriteBatch.begin();

        // Draw background
        for (int i = 0; i <= model.viewport.getWorldWidth() / model.getBackground().getWidth(); i++)
            sr.spriteBatch.draw(model.getBackground(), model.camera.position.x - model.viewport.getWorldWidth() / 2.0F + i * model.getBackground().getWidth(), -model.viewport.getWorldHeight() / 2.0F);

        sr.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        model.getTubeGates().draw(delta);
        model.getGround().draw(delta);
        model.getBird().draw(delta);
        sr.spriteBatch.end();
        sr.shapeRenderer.end();
        model.uiStage.draw();
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        model.viewport.update(width, height, false);
        model.uiStage.getViewport().update(width, height, true);
        model.worldChanged();
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
