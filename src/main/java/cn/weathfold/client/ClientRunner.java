/**
 * 
 */
package cn.weathfold.client;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cn.weathfold.critengine.CritEngine;

/**
 * 用于测试的客户端启动~
 * @author WeAthFolD
 *
 */
public class ClientRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display.setTitle("CritEngine Demo");
		try {
			Display.setDisplayMode(new DisplayMode(853, 480));
		} catch(Exception e) {
			e.printStackTrace();
		}
		CritEngine.start(new SceneMain());
	}

}
