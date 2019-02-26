package com.uf_bird.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uf_bird.game.UfBird;
import com.uf_bird.game.utilities.SharedResources;

import java.util.Stack;

public class ViewControllersManager extends UfBird {
    private SharedResources sr;
    private final Stack<Controller> controllers = new Stack<Controller>();

    @Override
    public void create() {
        sr = new SharedResources(this, UfBird.class.getCanonicalName());
        set(new MenuController(sr));
    }


    public void push(Controller controller) {
        controllers.push(controller);
    }

    public void pop() {
        controllers.pop().dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for (int i = 0; i < controllers.size(); i++) {
            controllers.elementAt(i).resize(width, height);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        for (int i = 0; i < controllers.size(); i++) {
            controllers.elementAt(i).render();
        }
    }

    @Override
    public void pause() {
        super.pause();
        for (int i = 0; i < controllers.size(); i++) {
            controllers.elementAt(i).pause();
        }
    }

    @Override
    public void resume() {
        super.resume();
        for (int i = 0; i < controllers.size(); i++) {
            controllers.elementAt(i).resume();
        }
    }

    public void set(Controller controller) {
        clear();
        push(controller);
    }

    public void clear() {
        while (!controllers.empty()) {
            pop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        clear();
        sr.dispose();
    }
}

