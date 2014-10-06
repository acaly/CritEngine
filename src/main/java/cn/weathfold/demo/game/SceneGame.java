/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.CritEngine;
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
import cn.weathfold.demo.game.gui.GUIHealth;
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
		SND_BGM0 = "bgm0",
		SND_GUNFIRE = "gunfire";
	
	public static final String
		TEX_BACKGROUND = "back",
		TEX_MIDGROUND = "mid",
		TEX_FOREGROUND = "fore",
		TEX_PLAYER_SHADOW = "pshadow",
		TEX_DOGE = "doge",
		TEX_EDGE = "edge",
		TEX_HPBAR = "hpbar";

	public static String
		TEX_PLAYER_FIRE[],
		TEX_PLAYER[],
		TEX_HP[];
	
	public LoopAnimation 
		animNormal,
		animShooting;
	
	public static double SCENE_HEIGHT = 285.0;
	
	private long sndPlayTime;
	
	public EntityPlayer thePlayer;
	
	private CameraGameGUI cameraGUI;
	private ObstacleFactory obstacles;
	
	/**
	 * 
	 */
	public SceneGame() {
		this.mainCamera = new CameraGame(this);
		//this.mainCamera = new KeyControlledCamera(this, -512, 0, 819, 512, Alignment.ALIGN_WIDTH);
		this.elements.add(mainCamera);
		
		thePlayer = new EntityPlayer(this);
		elements.add(thePlayer);
		
		cameraGUI = new CameraGameGUI(this);
		
		ObstacleTemplate doge = ObstacleFactory.getTemplate(0);
		doge.generate(this, -1024, 30);
		doge.generate(this, -800, 100);
		
		this.obstacles = new ObstacleFactory(this);
	}
	
	@Override
	public void frameUpdate() {
		long time = CritEngine.getVirtualTime();
		if(sndPlayTime == 0 || time - sndPlayTime > 60000) {
			sndPlayTime = time;
			CESoundEngine.playGlobalSound(SND_BGM0, new SoundAttributes(60000));
		}
		obstacles.generateTo(mainCamera.getX());
	}
	
	public void gameOver() {
		
	}
	
	@Override
	public void renderBackground() {
		//背景层绘制
		double cameraX = mainCamera.getGeomProps().getMinX() / 1035;
		final double scale = 819D / 512D / 2D;
		double uFore = cameraX,
			   uMid = cameraX * 0.2,
			   uBack = cameraX * 0.05;
		
		CERenderEngine.bindTexture(TEX_BACKGROUND);
		RenderUtils.renderTexturedQuads(0, 0, 819, 512, uBack, 0, uBack + scale, 1);
		
		CERenderEngine.bindTexture(TEX_MIDGROUND);
		RenderUtils.renderTexturedQuads(0D, 0D, 819, 512, uMid, 0, uMid + scale, 1);
		
		CERenderEngine.bindTexture(TEX_FOREGROUND);
		RenderUtils.renderTexturedQuads(0D, 0D, 819, 512, uFore, 0, uFore + scale, 1);
	}
	
	@Override
	public void preloadResources(ResourcePool pool) {
		//BGM
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH +"sounds/game/bgm0.wav"), SND_BGM0);
		
		//各种声音
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH +"sounds/game/gunfire.wav"), SND_GUNFIRE);
		
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
		
		//GUI
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/hpbar.png"), TEX_HPBAR);
		paths = new String[3];
		for(int i = 0; i < 3; ++i) {
			paths[i] = Type24.ASSETS_PATH + "textures/game/hp_" + i + ".png";
		}
		TEX_HP = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "hp");
	}

}
