package com.uf_bird.game.models;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.uf_bird.game.controllers.Controller;
import com.uf_bird.game.utilities.SharedResources;

import java.util.Vector;

public class TubeGates extends Model implements Entity {
    private static final float kGatesInterval = 50;

    private final Array<Polygon> boundsArray = new Array<Polygon>();
    private final Vector<TubeGate> tubeGates = new Vector<TubeGate>();

    private float groundY;
    private float gapFluctuation;

    TubeGates(SharedResources sr, Controller<?, ?> controller, float x, float y) {
        super(sr, controller);
        setPosition(x, y);
    }

    public void setWorldParams(float worldWidth, float worldHeight) {
        TubeGate referenceTubeGate = new TubeGate(sr, controller, getX(), getY());

        int currentGatesCount = tubeGates.size();
        float tubeWidth = referenceTubeGate.getWidth();
        int newGatesCount = (int) Math.ceil(worldWidth / (tubeWidth + kGatesInterval)) + 1;

        gapFluctuation = referenceTubeGate.getGapFluctuation(worldHeight);
        newGatesCount = Math.max(1, newGatesCount);
        tubeGates.setSize(newGatesCount);
        if (currentGatesCount < newGatesCount) {
            float startX;
            for (int i = currentGatesCount - 1; i < newGatesCount - 1; i++) {
                if (i < 0) {
                    startX = getX();
                } else {
                    startX = tubeGates.get(i).getX();
                }
                TubeGate newTubeGate = new TubeGate(sr, controller, startX + tubeWidth + kGatesInterval, getNextGapY());
                tubeGates.set(i + 1, newTubeGate);
            }
        }

        boundsArray.clear();
        for (TubeGate tubeGate : tubeGates) {
            boundsArray.addAll(tubeGate.getBounds());
        }
    }

    public void setGroundLevel(float groundLevel) {
        groundY = Math.round(groundLevel);
    }

    public float getNextGapY() {
        float min = -gapFluctuation + groundY;
        float max = gapFluctuation;
        return sr.random.nextFloat() * (max - min) + min;
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

    public boolean passedGate(Entity entity) {
        return tubeGates.firstElement().inGate(entity);
    }

    @Override
    public boolean collides(Entity entity) {
        for (TubeGate tubeGate : tubeGates) {
            if (tubeGate.collides(entity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Polygon[] getBounds() {
        return boundsArray.toArray(Polygon.class);
    }

    @Override
    public void draw(float delta) {
        for (TubeGate tubeGate : tubeGates) {
            tubeGate.draw(delta);
        }
    }

    @Override
    public synchronized void update(float delta) {
        update(delta, 0);
    }

    public synchronized void update(float delta, float worldX) {
        float tubeWidth = tubeGates.firstElement().getWidth();
        if (tubeGates.firstElement().getX() + tubeWidth < worldX) {
            tubeGates.remove(0);

            TubeGate newTubeGate = new TubeGate(sr, controller, tubeGates.lastElement().getX() + tubeWidth + kGatesInterval, getNextGapY());
            tubeGates.add(newTubeGate);
            boundsArray.removeRange(0, 1);
            boundsArray.addAll(newTubeGate.getBounds());
        }
    }

    @Override
    public void dispose() {
    }
}
