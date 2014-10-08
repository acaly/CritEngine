package cn.weathfold.demo.pause;

import cn.weathfold.critengine.CEUpdateProcessor;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.Type24;
import cn.weathfold.demo.game.SceneGame;

/**
 * 游戏暂停界面
 * @author WeAthFolD
 */
public class ScenePause extends Scene {
	
	//贴图
	public static final String 
		TEX_MAIN = "pause_main",
		TEX_RESUME0 = "pause_resume0",
		TEX_RESUME1 = "pause_resume1",
		TEX_MAINMENU0 = "pause_mainmenu0",
		TEX_MAINMENU1 = "pause_mainmenu1",
		TEX_QUIT0 = "pause_quit0",
		TEX_QUIT1 = "pause_quit1";
	
	//主场景
	public SceneGame gameScene;

	public ScenePause(SceneGame scene) {
		this.gameScene = scene;
		this.mainCamera = new Camera(this, 0.0D, 0.0D, 819.0D, 512.0D, Camera.Alignment.ALIGN_WIDTH);
		spawnEntity(new ButtonResume(this));
		spawnEntity(new ButtonMainmenu(this));
		spawnEntity(new ButtonQuit(this));
	}

	@Override
	public void frameUpdate() {
		CEUpdateProcessor.manualUpdateEntities(this);
	}

	@Override
	public void preloadResources(ResourcePool pool) {
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/back.png"), TEX_MAIN);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/resume0.png"), TEX_RESUME0);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/resume1.png"), TEX_RESUME1);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/mainmenu0.png"), TEX_MAINMENU0);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/mainmenu1.png"), TEX_MAINMENU1);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/quit0.png"), TEX_QUIT0);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/pause/quit1.png"), TEX_QUIT1);
	}
	
	@Override
	public void renderBackground() {
		CERenderEngine.bindTexture(TEX_MAIN);
		RenderUtils.renderTexturedQuads(153.5D, 0.0D, 665.5D, 512.0D);
	}
}