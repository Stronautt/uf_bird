package com.uf_bird.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uf_bird.game.controllers.ViewControllersManager;

public class DesktopLauncher {
    private static final int kWidth_ = 1920;
    private static final int kHeight = 1080;

    private static final String kTitle_ = "UF Bird";

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = kWidth_;
        config.height = kHeight;
        config.title = kTitle_;
        config.addIcon("icons/128x128.png", FileType.Internal);
        config.addIcon("icons/32x32.png", FileType.Internal);
        config.addIcon("icons/16x16.png", FileType.Internal);
        new LwjglApplication(new ViewControllersManager(), config);
	}
}
