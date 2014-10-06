/**
 * 
 */
package cn.weathfold.demo.game;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class CameraGameGUI extends Camera {

	public CameraGameGUI(SceneGame scene) {
		super(scene, 0, 0, 819, 512, Alignment.ALIGN_WIDTH);
	}
	
	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		GL11.glPopMatrix();
	}

}
