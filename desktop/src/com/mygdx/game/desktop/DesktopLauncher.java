package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;
import java.awt.Dimension;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Nation Rising";
                config.fullscreen = true;
                
                //Set game size to screen size
                Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                config.width = screen.width;
                config.height = screen.height;
		new LwjglApplication(new Main(), config);
	}
}