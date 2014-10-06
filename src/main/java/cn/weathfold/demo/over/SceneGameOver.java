/**
 * 
 */
package cn.weathfold.demo.over;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.Type24;
import cn.weathfold.demo.menu.SceneMenu;

/**
 * "游戏结束"界面，也是Scene中Scene的典型范例
 * @author WeAthFolD
 *
 */
public class SceneGameOver extends Scene {
	
	protected static final String
		TEX_MAIN = "over_main",
		TEX_INFO = "over_info";
	
	int score;
	
	EntityInfo info = new EntityInfo(this);
	
	private KeyEventProducer keyListener = new KeyEventProducer() {
		
		{
			this.addKeyListening(Keyboard.KEY_ESCAPE);
		}

		@Override
		public void onKeyDown(int kid) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyFrame(int kid) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKeyUp(int kid) {
			CritEngine.switchScene(SceneMenu.INSTANCE);
		}
		
	};

	public SceneGameOver() {
	}
	
	public void setScore(int i) {
		score = i;
	}
	
	@Override
	public void frameUpdate() {
		keyListener.frameUpdate();
	}
	
	@Override
	public void renderBackground() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(0F, 0F, 0F, 0.4F);
		GL11.glPushMatrix(); {
			RenderUtils.renderTexturedQuads(0, 0, 819, 512);
		} GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	@Override
	public void renderForeground() {
		GL11.glPushMatrix(); {
			GL11.glColor4f(1F, 1F, 1F, 1F);
			CERenderEngine.bindTexture(TEX_MAIN);
			RenderUtils.renderTexturedQuads(0 + 153.5, 0, 512 + 153.5, 512);
			
			info.drawEntity();
		} GL11.glPopMatrix();
	}
	
	public void preloadResources(ResourcePool pool) {
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/over/main.png"), TEX_MAIN);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/over/info.png"), TEX_INFO);
	}

}
