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
	
	public static boolean AUTO_QUIT;
	
	public static class MyScene extends GUIScene {
		
		private long initTime;
		
		public MyScene() {
			this.initTime = CritEngine.getVirtualTime();
		}
		
		public void frameUpdate() {
			if(AUTO_QUIT && CritEngine.getVirtualTime() - initTime > 1000) {
				Display.destroy();
				System.exit(0);
			}
			super.frameUpdate();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display.setTitle("CritEngine Demo");
		AUTO_QUIT = args.length >= 1 && args[0].equals("test");
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
		} catch(Exception e) {
			e.printStackTrace();
		}
		CritEngine.start(new MyScene());
	}

}
