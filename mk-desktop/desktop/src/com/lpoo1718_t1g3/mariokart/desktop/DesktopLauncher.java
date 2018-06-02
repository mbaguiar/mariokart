package com.lpoo1718_t1g3.mariokart.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mario Kart";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = false;
		new LwjglApplication(MarioKart.getInstance(), config);
	}
}
