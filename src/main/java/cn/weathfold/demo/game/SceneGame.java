/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.EntitySolid;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.render.animation.RandomAnimation;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.resource.WavSoundObject;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.Type24;
import cn.weathfold.demo.game.obstacle.ObstacleFactory;
import cn.weathfold.demo.game.obstacle.ObstacleTemplate;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 游戏的主场景~
 * @author WeAthFolD
 *
 */
public class SceneGame extends Scene {
	
	public static final String
		SND_BGM0 = "bgm0";
	
	public static final String
		TEX_BACKGROUND = "back",
		TEX_MIDGROUND = "mid",
		TEX_FOREGROUND = "fore",
		TEX_PLAYER_SHADOW = "pshadow",
		TEX_DOGE = "doge",
		TEX_EDGE = "edge";

	public static String
		TEX_PLAYER_FIRE[],
		TEX_PLAYER[];
	
	public LoopAnimation 
		animNormal,
		animShooting;
	
	public static int SCENE_HEIGHT = 285;
	
	private long sndPlayTime;
	
	/**
	 * 
	 */
	public SceneGame() {
		this.mainCamera = new CameraGame(this);
		//this.mainCamera = new KeyControlledCamera(this, -512, 0, 819, 512, Alignment.ALIGN_WIDTH);
		this.elements.add(mainCamera);
		this.elements.add(new EntityPlayer(this));
		System.out.println("ObstacleFactory size " + ObstacleFactory.getTemplateCount());
		ObstacleTemplate doge = ObstacleFactory.getTemplate(0);
		doge.generate(this, -1024, 30);
		doge.generate(this, -800, 100);
	}
	
	@Override
	public void frameUpdate() {
		long time = CritEngine.getVirtualTime();
		if(sndPlayTime == 0 || time - sndPlayTime > 60000) {
			sndPlayTime = time;
			CESoundEngine.playGlobalSound(SND_BGM0, new SoundAttributes(60000));
		}
	}
	
	public void gameOver() {
		
	}
	
	@Override
	public void renderBackground() {
		//背景层绘制
		double cameraX = mainCamera.getGeomProps().getMinX() / 1638D;
		double uFore = cameraX,
			   uMid = cameraX * 0.2,
			   uBack = cameraX * 0.05;
		CERenderEngine.bindTexture(TEX_BACKGROUND);
		RenderUtils.renderTexturedQuads(0, 0, 1, 1, uBack, 0, uBack + 0.5, 1);
		
		CERenderEngine.bindTexture(TEX_MIDGROUND);
		RenderUtils.renderTexturedQuads(0D, 0D, 1D, 1, uMid, 0, uMid + .5, 1);
		
		CERenderEngine.bindTexture(TEX_FOREGROUND);
		RenderUtils.renderTexturedQuads(0D, 0D, 1D, 1, uFore, 0, uFore + .5, 1);
	}
	
	public void preloadResources(ResourcePool pool) {
		//BGM
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH +"sounds/game/bgm0.wav"), SND_BGM0);
		
		//背景s
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/back.png"), TEX_BACKGROUND);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/middle.png"), TEX_MIDGROUND);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/front.png"), TEX_FOREGROUND);
		
		//玩家动画
		String paths[];
		paths = new String[2];
		for(int i = 0; i < 2; ++i) {
			paths[i] = Type24.ASSETS_PATH + "textures/game/player_fire" + i + ".png";
		}
		TEX_PLAYER_FIRE = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "player_fire");
		
		paths = new String[4];
		for(int i = 0; i < 4; ++i) {
			paths[i] = Type24.ASSETS_PATH + "textures/game/player" + i + ".png";
		}
		TEX_PLAYER = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "player");
		
		
		Rect drawBound = new Rect(0, 0, EntityPlayer.HEIGHT, EntityPlayer.HEIGHT);
		animNormal = new RandomAnimation(SceneGame.TEX_PLAYER).setDrawingQuad(drawBound);
		animShooting = new LoopAnimation(SceneGame.TEX_PLAYER_FIRE).setDrawingQuad(drawBound);
		
		//System.out.println(TEX_PLAYER.length);
		//System.out.println(TEX_PLAYER_FIRE.length);
		//杂项
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/doge.png"), TEX_DOGE);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/player_shadow.png"), TEX_PLAYER_SHADOW);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/edge.png"), TEX_EDGE);
	}

}
