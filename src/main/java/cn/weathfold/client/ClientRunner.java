/**
 * 
 */
package cn.weathfold.client;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.GUIScene;

/**
 * 用于测试的客户端启动~
 * @author WeAthFolD
 *
 */
public class ClientRunner {
	
	public static final boolean AUTO_QUIT = true;
	
	public static class MyScene extends GUIScene {
		
		private long initTime;
		
		public MyScene() {
			this.initTime = CritEngine.getVirtualTime();
		}
		
		public void frameUpdate() {
			if(CritEngine.getVirtualTime() - initTime > 1000) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display.setTitle("CritEngine Demo");
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
		} catch(Exception e) {
			e.printStackTrace();
		}
		CritEngine.start(new MyScene());
	}

}
