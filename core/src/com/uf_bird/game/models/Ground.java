package com.uf_bird.game.models;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.utilities.SharedResources;

public class Ground extends Model implements Entity {
    public static final float kHeight = 20;

    private final NinePatch ground;
    private final Polygon bounds = new Polygon();
    private final Polygon[] boundsArray;

    private int count;

    public Ground(SharedResources sr, Controller<?, ?> controller) {
        super(sr, controller);
        Skin gameTexturesSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);
        ground = gameTexturesSkin.get("ground", NinePatch.class);
        boundsArray = new Polygon[]{bounds};
    }

    public void setWorldWidth(float worldWidth) {
        bounds.setVertices(new float[]{
                0, kHeight,
                worldWidth, kHeight,
                worldWidth, 0,
                0, 0
        });
        count = (int) Math.ceil(worldWidth / ground.getTotalWidth()) + 1;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.setPosition(x, y - kHeight);
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
            if (Intersector.overlapConvexPolygons(bounds, polygon)) {
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
        for (int i = 0; i <= count; i++) {
            ground.draw(sr.spriteBatch, getX() + i * ground.getTotalWidth(), getY() - kHeight, ground.getTotalWidth(), kHeight);
        }
        if (sr.kDrawDebug) {
            sr.shapeRenderer.polygon(bounds.getTransformedVertices());
        }
    }

    @Override
    public synchronized void update(float delta) {
        update(delta, 0);
    }

    public synchronized void update(float delta, float worldX) {
        bounds.setPosition(worldX + 1, bounds.getY());
        if (getX() + ground.getTotalWidth() < worldX) {
            setPosition(getX() + ground.getTotalWidth(), getY());
        }
    }

    @Override
    public void dispose() {
    }
}
