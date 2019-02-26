package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.utilities.SharedResources;

public class TubeGate extends Model implements Entity {
    private static final float kGapHeight = 50;
    private static final float kTubeHeight = 99999999;

    private final NinePatch tube;
    private final Polygon topBounds, bottomBounds, gapBounds;
    private final Polygon[] boundsArray;

    private boolean isPassed;

    public TubeGate(SharedResources sr, Controller<?, ?> controller, float x, float y) {
        super(sr, controller);

        Skin gameSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);

        tube = gameSkin.get("tube", NinePatch.class);
        topBounds = new Polygon(new float[]{
                2, 0,
                tube.getTotalWidth(), 0,
                tube.getTotalWidth(), getHeight() - 3,
                tube.getTotalWidth() - 2, getHeight() - 3,
                tube.getTotalWidth() - 2, kTubeHeight,
                2, getHeight() - 3,
                0, getHeight() - 3,
                0, 0
        });
        bottomBounds = new Polygon(topBounds.getVertices());
        bottomBounds.setOrigin(tube.getTotalWidth() / 2.0F, 0);
        bottomBounds.setRotation(180);

        gapBounds = new Polygon(new float[]{
                0, 0,
                getWidth() / 4.0F, 0,
                getWidth() / 4.0F, kGapHeight,
                0, kGapHeight
        });
        setPosition(x, y);
        boundsArray = new Polygon[]{topBounds, bottomBounds};
    }

    public boolean inGate(Entity entity) {
        if (!isPassed) {
            for (Polygon bounds : entity.getBounds()) {
                if (Intersector.overlapConvexPolygons(gapBounds, bounds)) {
                    isPassed = true;
                    return true;
                }
            }
        }
        return false;
    }

    public float getGapFluctuation(float worldHeight) {
        return Math.round(worldHeight / 2.0F) - (int) kGapHeight / 2 - (int) getHeight();
    }

    public float getWidth() {
        return tube.getTotalWidth();
    }

    public float getHeight() {
        return tube.getTotalHeight();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        topBounds.setPosition(x, y + kGapHeight / 2.0F);
        bottomBounds.setPosition(x, y - kGapHeight / 2.0F);
        gapBounds.setPosition(x + 3.0F * getWidth() / 4.0F, y - kGapHeight / 2.0F);
    }

    // Rotation is not supported
    @Override
    public float getRotation() {
        return 0;
    }

    // Rotation is not supported
    @Override
    public void setRotation(float degrees) {
    }

    @Override
    public boolean collides(Entity entity) {
        for (Polygon polygon : entity.getBounds()) {
            if (Intersector.overlapConvexPolygons(topBounds, polygon) ||
                    Intersector.overlapConvexPolygons(bottomBounds, polygon)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Polygon[] getBounds() {
        return boundsArray;
    }

    @Override
    public void draw(float delta) {
        tube.draw(sr.spriteBatch, topBounds.getX(), topBounds.getY(), tube.getTotalWidth(), kTubeHeight);
        tube.draw(sr.spriteBatch,
                bottomBounds.getX(),
                bottomBounds.getY(),
                tube.getTotalWidth() / 2.0F,
                0, tube.getTotalWidth(),
                kTubeHeight,
                -1,
                1,
                180);
        if (sr.kDrawDebug) {
            sr.shapeRenderer.polygon(topBounds.getTransformedVertices());
            sr.shapeRenderer.polygon(bottomBounds.getTransformedVertices());
            sr.shapeRenderer.polygon(gapBounds.getTransformedVertices());
        }
    }

    @Override
    public synchronized void update(float delta) {
    }

    @Override
    public void dispose() {
    }
}
