/**
 * 
 */
package cn.weathfold.demo;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.demo.menu.SceneMenu;

/**
 * @author WeAthFolD
 *
 */
public class Type24 {
	
	public static final String
		ASSETS_PATH = "/assets/type24/";

	public static void main(String[] args) {
		new Type24().run();
	}
	
	public void run() {
		Display.setTitle("CritEngine : Type 24");
		try {
			Display.setDisplayMode(new DisplayMode(512, 512));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		CritEngine.start(new SceneMenu());
	}

}
