package com.uf_bird.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class MusicMixer {
    private final Settings settings;
    private final HashMap<String, Disposable> tracks = new HashMap<String, Disposable>();

    public MusicMixer(Settings settings) {
        this.settings = settings;
    }

    public <T> void load(FileHandle fileHandle, Class<T> type) {
        load(fileHandle.nameWithoutExtension(), fileHandle, type);
    }

    public <T> void load(String alias, FileHandle fileHandle, Class<T> type) {
        if (type == Music.class) {
            tracks.put(alias, Gdx.audio.newMusic(fileHandle));
        } else if (type == Sound.class) {
            tracks.put(alias, Gdx.audio.newSound(fileHandle));
        }
    }

    public void play(String name) {
        if (tracks.get(name) instanceof Music && settings.musicEnabled()) {
            ((Music) tracks.get(name)).play();
        } else if (tracks.get(name) instanceof Sound && settings.soundEnabled()) {
            ((Sound) tracks.get(name)).play();
        }
    }

    public boolean isPlaying(String name) {
        if (tracks.get(name) instanceof Music && settings.musicEnabled()) {
            return ((Music) tracks.get(name)).isPlaying();
        }
        return false;
    }

    public void stop(String name) {
        if (tracks.get(name) instanceof Music) {
            ((Music) tracks.get(name)).stop();
        } else if (tracks.get(name) instanceof Sound) {
            ((Sound) tracks.get(name)).stop();
        }
    }

    public <T> void stopAll(Class<T> type) {
        for (HashMap.Entry<String, Disposable> track : tracks.entrySet()) {
            Disposable trackValue = track.getValue();
            if (type == Music.class && trackValue instanceof Music) {
                ((Music) trackValue).stop();
            } else if (type == Sound.class && trackValue instanceof Sound) {
                ((Sound) trackValue).stop();
            }
        }
    }

    public void setLooping(String name, boolean looping) {
        ((Music) tracks.get(name)).setLooping(looping);
    }

    public void setLooping(String name, int soundId, boolean looping) {
        ((Sound) tracks.get(name)).setLooping(soundId, looping);
    }

    public void dispose() {
        for (HashMap.Entry<String, Disposable> track : tracks.entrySet()) {
            track.getValue().dispose();
        }
    }
}
