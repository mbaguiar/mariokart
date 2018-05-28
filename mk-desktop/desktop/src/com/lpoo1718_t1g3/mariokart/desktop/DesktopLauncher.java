package com.lpoo1718_t1g3.mariokart.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 1024;
		new LwjglApplication(MarioKart.getInstance(), config);
	}
}
