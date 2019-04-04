package com.uf_bird.game.controllers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uf_bird.game.models.SettingsModel;
import com.uf_bird.game.utilities.SharedResources;
import com.uf_bird.game.views.SettingsView;

public class SettingsController extends Controller<SettingsModel, SettingsView> {
    public SettingsController(SharedResources sr) {
        super(sr);
        model = new SettingsModel(sr, this);
        setScreen(new SettingsView(sr, this, model));
    }

    public ChangeListener enableSoundCheckBoxTriggered() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.stopAll(Sound.class);
                sr.settings.soundEnabled(((CheckBox) actor).isChecked());
            }
        };
    }

    public ChangeListener enableMusicCheckBoxTriggered() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.stopAll(Music.class);
                sr.settings.musicEnabled(((CheckBox) actor).isChecked());
            }
        };
    }

    public ChangeListener dayTimeSelectBoxTriggered() {
        return new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                sr.settings.dayTime(((SelectBox) actor).getSelected().toString());

            }
        };
    }

    public ChangeListener menuButtonPressed() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sr.musicMixer.play("swooshing");
                sr.vcm.set(new MenuController(sr));
            }
        };
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void create() {
    }
}
