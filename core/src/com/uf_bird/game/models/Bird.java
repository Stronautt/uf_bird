package com.uf_bird.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.utilities.SharedResources;

public class Bird extends Model implements Entity {
    private static final float kMovement = 0.8F;
    private static final float kJumpHeight = 120.0F;
    private static final float kMaxRotationAngle = 45.0F; // degrees
    private static final float kFlyAnimationSpeed = 0.1F;

    private final Vector2 velocity = new Vector2();
    private final Animation<TextureRegion> flyAnimation;
    private final Polygon bounds;
    private final Polygon[] boundsArray;
    private float stateTime, width, height;
    private boolean dead;
    private boolean stopped = true;

    public Bird(SharedResources sr, Controller<?, ?> controller) {
        super(sr, controller);

        Skin gameSkin = sr.assetManager.get(sr.kDefaultSkin, Skin.class);
        Array<TextureRegion> birdAnimation = gameSkin.getRegions("bird." + sr.random.nextInt(3));
        flyAnimation = new Animation<TextureRegion>(kFlyAnimationSpeed, birdAnimation, PlayMode.LOOP);
        TextureRegion firstFrame = flyAnimation.getKeyFrame(stateTime);
        width = firstFrame.getRegionWidth();
        height = firstFrame.getRegionHeight();
        bounds = new Polygon(new float[]{
                0, height / 2,
                width / 2, 0,
                width, height / 2,
                width / 2, height});
        boundsArray = new Polygon[]{bounds};
        bounds.setOrigin(width / 2.0F, height / 2.0F);
        setPosition(0, 0);
    }

    @Override
    public synchronized void update(float delta) {
        act(delta);
        if (delta <= 0) {
            return;
        }
        velocity.add(0, GameModel.kGravity.y);
        velocity.scl(delta);
        if (dead) {
            setPosition(getX(), getY() + velocity.y);
        } else if (!stopped) {
            setPosition(getX() + kMovement + delta, getY() + velocity.y);
        }

        if (stopped) {
            return;
        }

        velocity.scl(1.0F / delta);
        float rotation_angle = velocity.y / (kJumpHeight * 1.5F) * kMaxRotationAngle;
        setRotation(Math.min(Math.max(rotation_angle, -kMaxRotationAngle), kMaxRotationAngle));
    }

    @Override
    public void dispose() {
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.setPosition(getX() - width / 2.0F, getY() - height / 2.0F);
    }

    @Override
    public float getRotation() {
        return bounds.getRotation();
    }

    @Override
    public void setRotation(float degrees) {
        bounds.setRotation(sr.lerp(getRotation(), degrees, 0.35F));
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
        stateTime += delta;
        TextureRegion currentFrame = flyAnimation.getKeyFrame(stateTime, true);
        sr.spriteBatch.draw(currentFrame,
                getX() - width / 2.0F,
                getY() - height / 2.0F,
                currentFrame.getRegionWidth() / 2.0f,
                currentFrame.getRegionHeight() / 2.0f,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                getScaleX() * (currentFrame.getRegionHeight() / (float) currentFrame.getRegionWidth()),
                getScaleY() * (2 - (currentFrame.getRegionHeight() / (float) currentFrame.getRegionWidth())),
                bounds.getRotation() - 90,
                false);
        if (sr.kDrawDebug) {
            if (sr.shapeRenderer.isDrawing()) {
                sr.shapeRenderer.polygon(bounds.getTransformedVertices());
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!batch.isDrawing()) {
            batch.begin();
            draw(Gdx.graphics.getDeltaTime());
            batch.end();
        } else {
            draw(Gdx.graphics.getDeltaTime());
        }
    }

    public void jump() {
        if (!dead && !stopped) {
            velocity.y = kJumpHeight;
            sr.musicMixer.play("wing");
        }
    }

    public boolean isAlive() {
        return !dead;
    }

    public void die() {
        dead = true;
        sr.musicMixer.play("die");
    }

    public void goForward() {
        stopped = false;
    }
}
