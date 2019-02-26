package com.uf_bird.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Settings {
    private static final String kTexturesPath = "data/atlases/";

    private final Preferences preferences;

    public Settings(String name) {
        preferences = Gdx.app.getPreferences(name);
    }

    public boolean soundEnabled() {
        return preferences.getBoolean("soundEnabled", true);
    }

    public void soundEnabled(boolean val) {
        preferences.putBoolean("soundEnabled", val).flush();
    }

    public boolean musicEnabled() {
        return preferences.getBoolean("musicEnabled", true);
    }

    public void musicEnabled(boolean val) {
        preferences.putBoolean("musicEnabled", val).flush();
    }

    public String gameTexturesPack() {
        return preferences.getString("texturesPack", kTexturesPath + "defaultTextures.atlas");
    }

    public void gameTexturesPack(String val) {
        preferences.putString("texturesPack", kTexturesPath + val + ".atlas").flush();
    }

    public int gameMaxScore() {
        return Integer.parseInt(preferences.getString("gameMaxScore", "0"));
    }

    public void gameMaxScore(int val) {
        preferences.putString("gameMaxScore", val + "").flush();
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
