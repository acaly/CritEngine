/**
 * 
 */
package cn.weathfold.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
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
		}
		
		@Override
		public void frameUpdate() {
			
			boolean b = Keyboard.isKeyDown(Keyboard.KEY_M);
			if(b && !keyState) {
				
			}
			keyState = b;
			
			super.frameUpdate();
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
		CritEngine.start(new MyScene());
	}

}
