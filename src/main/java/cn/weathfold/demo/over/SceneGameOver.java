package cn.weathfold.demo.over;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.Type24;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.mainmenu.SceneMainMenu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 * 游戏结束的子界面。
 * @author WeAthFolD
 */
public class SceneGameOver extends Scene {
	
	protected static final String TEX_MAIN = "over_main";
	protected static final String TEX_INFO = "over_info";
	int score; //愉快的分数
	EntityInfo info = new EntityInfo(this);

	private KeyEventProducer keyListener = new KeyEventProducer() {
		{
			this.addKeyListening(Keyboard.KEY_ESCAPE);
		}

		@Override
		public void onKeyDown(int kid) {
		}

		@Override
		public void onKeyFrame(int kid) {
		}

		@Override
		public void onKeyUp(int kid) {
			CritEngine.switchScene(SceneMainMenu.INSTANCE);
		}
	};
	SceneGame gameScene;
	Color fontColor = new Color(255, 240, 193, 255);

	public SceneGameOver(SceneGame scene) {
		this.gameScene = scene;
	}

	@Override
	public void frameUpdate() {
		this.keyListener.frameUpdate();
	}
	
	@Override
	public void onSwitchedScene() {
		this.score = gameScene.calculateScore();
	}

	@Override
	public void renderBackground() {
		GL11.glDisable(3553);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glPushMatrix();
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D);
		GL11.glPopMatrix();
		GL11.glEnable(3553);
	}

	@Override
	public void renderForeground() {
		GL11.glPushMatrix(); {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			CERenderEngine.bindTexture(SceneGameOver.TEX_MAIN);
			RenderUtils.renderTexturedQuads(153.5D, 0.0D, 665.5D, 512.0D);

			float size = 60.0F;
			CERenderEngine.switchFont("Coppplate Gothic Bold");
			String str = String.valueOf(score);
			double len = CERenderEngine.getStringLength(str, size);
			CERenderEngine.drawString(409.5D - len / 2.0D, 250.0D, str, size, this.fontColor);

			this.info.drawEntity();
		} GL11.glPopMatrix();
	}

	@Override
	public void preloadResources(ResourcePool pool) {
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/over/main.png"), SceneGameOver.TEX_MAIN);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/over/info.png"), SceneGameOver.TEX_INFO);
		CERenderEngine.preloadFont("Copperplate Gothic Bold");
	}
}
