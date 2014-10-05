/**
 * 
 */
package cn.weathfold.client;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.resource.WavSoundObject;
import cn.weathfold.critengine.scene.GUIScene;

/**
 * 用于测试的客户端启动~
 * @author WeAthFolD
 *
 */
public class ClientRunner {
	public static class MyScene extends GUIScene {
		
		private boolean keyState;
		private final static String
			SND_A = "overfly";
		private final static String
			TEX_A = "sgate";
		
		
		public MyScene() {
			this.mainCamera = new Camera(this, 0, 0, 700, 1, Alignment.ALIGN_WIDTH);
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
		}
		
		public void renderBackground() {
			GL11.glPushMatrix(); {
				CERenderEngine.bindTexture(TEX_A);
				GL11.glColor4f(0.3F, 0.3F, 0.3F, 0.3F);
				
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0F, 1F); GL11.glVertex2f(0F, 0F);
				GL11.glTexCoord2f(1F, 1F); GL11.glVertex2f(1F, 0F);
				GL11.glTexCoord2f(1F, 0F); GL11.glVertex2f(1F, 1F);
				GL11.glTexCoord2f(0F, 0F); GL11.glVertex2f(0F, 1F);
				GL11.glEnd();
				
			} GL11.glPopMatrix();
		}
		
		protected Set<Entity> elements = new HashSet<Entity>();
		private float velFactor = 0.07F;
		
		@Override
		public Set<Entity> getSceneEntities() {
			return elements;
		}

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display.setTitle("CritEngine Demo");
		try {
			Display.setDisplayMode(new DisplayMode(1138, 640));
		} catch(Exception e) {
			e.printStackTrace();
		}
		CritEngine.start(new SceneMain());
	}

}
