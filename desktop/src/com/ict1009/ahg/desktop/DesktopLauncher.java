package com.ict1009.ahg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ict1009.ahg.AnimalHunter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 900;
		config.width = 600;
		config.resizable = false;
		new LwjglApplication(new AnimalHunter(), config);
	}
}
