/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.Entity;

/**
 * @author WeAthFolD
 *
 */
public class GUIScene extends Scene {
	
	protected Set<Entity> elements = new HashSet();
	private float velFactor = 0.07F;
	
	public GUIScene() {
		this.mainCamera = new Camera(this, 0, 0, 700, 1, Alignment.ALIGN_WIDTH);
	}
	
	public void frameUpdate() {
		boolean up = Keyboard.isKeyDown(Keyboard.KEY_UP),
				down = Keyboard.isKeyDown(Keyboard.KEY_DOWN),
				left = Keyboard.isKeyDown(Keyboard.KEY_LEFT),
				right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		long elapsed = CritEngine.getTimer().getElapsedTime();
		System.out.println("EL " + elapsed);
		float hr = elapsed * velFactor * ((left ? -1 : 0) + (right ? 1 : 0)),
				vr = elapsed * velFactor * ((up ? 1 : 0) + (down ? -1 : 0));
		System.out.println("[" + hr + ", " + vr + "]");
		mainCamera.addPosition(hr, vr);
	}

	@Override
	public Set<Entity> getSceneEntities() {
		return elements;
	}

	@Override
	public void renderScene() {
		GL11.glPushMatrix();
		GL11.glColor3f(0.8F, 0.8F, 0F);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glTranslated(300, 300, 0);
		//GL11.glRotated(((int)CritEngine.getVirtualTime() / 80D), 0, 0, 1);
		GL11.glBegin(GL11.GL_TRIANGLES); {
		
			GL11.glVertex3d(10, 10, 0);
			GL11.glVertex3d(100, 20, 0);
			GL11.glVertex3d(200, 300, 0);
		
		} GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

}
