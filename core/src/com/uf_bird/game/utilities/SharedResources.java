package com.uf_bird.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uf_bird.game.controllers.ViewControllersManager;

import java.util.Random;

public class SharedResources {
    public final boolean kDrawDebug = false;
    public final String kDefaultSkin = "data/skins/skin.json";

    public final Random random = new Random();
    public final SpriteBatch spriteBatch = new SpriteBatch();
    public final ShapeRenderer shapeRenderer = new ShapeRenderer();
    public final AssetManager assetManager = new AssetManager();
    public final ViewControllersManager vcm;
    public final Settings settings;
    public final MusicMixer musicMixer;

    public SharedResources(ViewControllersManager vcm, String settingsName) {
        this.vcm = vcm;
        settings = new Settings(settingsName);
        musicMixer = new MusicMixer(settings);
        assetManager.load(kDefaultSkin, Skin.class, new SkinLoader.SkinParameter(settings.gameTexturesPack()));

        musicMixer.load(Gdx.files.internal("data/sound/ost.mp3"), Music.class);
        musicMixer.setLooping("ost", true);
        musicMixer.load(Gdx.files.internal("data/sound/die.mp3"), Sound.class);
        musicMixer.load(Gdx.files.internal("data/sound/hit.mp3"), Sound.class);
        musicMixer.load(Gdx.files.internal("data/sound/point.mp3"), Sound.class);
        musicMixer.load(Gdx.files.internal("data/sound/swooshing.mp3"), Sound.class);
        musicMixer.load(Gdx.files.internal("data/sound/wing.mp3"), Sound.class);
        assetManager.finishLoading();
    }

    public float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

    public float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        assetManager.dispose();
        musicMixer.dispose();
    }
}
