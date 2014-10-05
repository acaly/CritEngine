/**
 * 
 */
package cn.weathfold.client;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class DebugClientRunner {
	
	public static class MyScene extends Scene {
		
		private long initTime;
		
		public MyScene() {
			this.initTime = CritEngine.getVirtualTime();
			this.mainCamera = new Camera(this, 0, 0, 400, 400, Alignment.ALIGN_WIDTH);
		}
		
		@Override
		public void frameUpdate() {
			if(CritEngine.getVirtualTime() - initTime > 1000) {
				Display.destroy();
				System.exit(0);
			}
			super.frameUpdate();
		}
	}
	
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		CritEngine.start(new MyScene());
	}
	
}
