package com.uf_bird.game.models;

import com.badlogic.gdx.math.Polygon;

public interface Entity {
    /**
     * Gets the rotation of the Entity.
     *
     * @return rotation angle in degrees.
     */
    float getRotation();

    /**
     * Sets the rotation of the Entity to the selected angle.
     *
     * @param degrees - rotation angle in degrees.
     */
    void setRotation(float degrees);

    /**
     * Checks if Entity collides with selected one.
     *
     * @param entity - Second entity
     * @return True if collides and false - otherwise.
     */
    boolean collides(Entity entity);

    /**
     * Gets the bounds of Entity.
     *
     * @return boundary polygon.
     */
    Polygon[] getBounds();

    /**
     * Draw entity using selected SpriteBatch.
     * @param batch
     */

    /**
     * Draw entity using selected SpriteBatch and/or ShapeRenderer.
     *
     * @param delta
     */
    void draw(float delta);
}
