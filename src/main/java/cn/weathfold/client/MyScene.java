/**
 * 
 */
package cn.weathfold.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.EntitySolid;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class MyScene extends Scene {
	
	private boolean keyState;
	private final static String
		SND_A = "overfly";
	private final static String
		TEX_A = "sgate",
		TEX_WALL = "wall",
		TEX_BALL = "ball";
	
	
	public MyScene() {
		this.mainCamera = new Camera(this, 0, 0, 600, 1, Alignment.ALIGN_WIDTH);
		this.elements.add(new EntitySolid(this, 0, 0, 600, 40).setTexture(TEX_WALL, 0, 0, 15, 1));
		this.elements.add(new EntityBouncingBall(this, 100, 200, 40).setVelocity(0, -1).setTexture(TEX_BALL));
	}
	
	@Override
	public void frameUpdate() {
		
		boolean b = Keyboard.isKeyDown(Keyboard.KEY_M);
		if(b && !keyState) {
			
		}
		keyState = b;
		boolean up = Keyboard.isKeyDown(Keyboard.KEY_UP),
				down = Keyboard.isKeyDown(Keyboard.KEY_DOWN),
				left = Keyboard.isKeyDown(Keyboard.KEY_LEFT),
				right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		long elapsed = CritEngine.getTimer().getElapsedTime();
		float hr = elapsed * velFactor * ((left ? -1 : 0) + (right ? 1 : 0)),
				vr = elapsed * velFactor * ((up ? 1 : 0) + (down ? -1 : 0));
		mainCamera.addPosition(hr, vr);
	}
	
	@Override
	public void preloadResources(ResourcePool pool) {
		//sounds
		//pool.preloadSound(new WavSoundObject("/assets/sounds/overfly.wav"), SND_A);
		
		//textures
		pool.preloadTexture(new PNGTextureObject("/assets/textures/back.png"), TEX_A);
		pool.preloadTexture(new PNGTextureObject("/assets/textures/wall.png"), TEX_WALL);
		pool.preloadTexture(new PNGTextureObject("/assets/textures/ball.png"), TEX_BALL);
	}
	
	public void renderBackground() {
		GL11.glPushMatrix(); {
			CERenderEngine.bindTexture(TEX_A);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
			RenderUtils.renderTexturedQuads(0, 0, 1, 1);
		} GL11.glPopMatrix();
	}
	
	private float velFactor = 0.07F;

	@Override
	public void renderScene() {
		GL11.glPushMatrix();
		GL11.glColor4f(0.8F, 0.8F, 0F, 1F);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glTranslated(300, 300, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glRotated(((int)CritEngine.getVirtualTime() / 80D), 0, 0, 1);
		GL11.glBegin(GL11.GL_TRIANGLES); {
		
			GL11.glVertex3d(10, 10, 0);
			GL11.glVertex3d(100, 20, 0);
			GL11.glVertex3d(200, 300, 0);
		
		} GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
