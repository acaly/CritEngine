/**
 * 
 */
package cn.weathfold.demo.game;

import org.lwjgl.input.Keyboard;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.input.KeyEventProducer;
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
import cn.weathfold.demo.over.SceneGameOver;

/**
 * 游戏的主场景~
 * @author WeAthFolD
 *
 */
public class SceneGame extends Scene {
	
	public static SceneGame INSTANCE;
	
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
		TEX_HP[],
		TEX_OBSTACLES[];
	
	public LoopAnimation 
		animNormal,
		animShooting;
	
	public static double SCENE_HEIGHT = 285.0;
	
	private long sndPlayTime;
	
	public EntityPlayer thePlayer;
	
	private CameraGameGUI cameraGUI;
	private ObstacleFactory obstacles;
	private SceneGameOver overScene = new SceneGameOver();
	private GUIHealth guiHealth;
	
	public boolean gameOver = false;
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
			if(!gameOver) {
				pause();
			}
		}
		
	};
	
	/**
	 * 
	 */
	public SceneGame() {
		this.mainCamera = new CameraGame(this);
		//this.mainCamera = new KeyControlledCamera(this, -512, 0, 819, 512, Alignment.ALIGN_WIDTH);
		
		thePlayer = new EntityPlayer(this);
		elements.add(mainCamera);
		elements.add(thePlayer);
		guiHealth = new GUIHealth(this);
		
		cameraGUI = new CameraGameGUI(this);
		
		this.obstacles = new ObstacleFactory(this);
		if(INSTANCE == null)
			INSTANCE = this;
	}
	
	@Override
	public void frameUpdate() {
		long time = CritEngine.getVirtualTime();
		keyListener.frameUpdate();
		
		if(gameOver) {
			overScene.frameUpdate();
			return;
		}
		
		if(sndPlayTime == 0 || time - sndPlayTime > 60000) {
			sndPlayTime = time;
			CESoundEngine.playGlobalSound(SND_BGM0, new SoundAttributes(60000));
		}
		obstacles.generateTo(mainCamera.getX());
	}
	
	public void gameOver() {
		gameOver = true;
	}
	
	public void pause() {
		
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
	public void renderForeground() {
		super.renderForeground();
		cameraGUI.draw();
		
		if(gameOver) {
			overScene.renderBackground();
			overScene.renderForeground();
		}
	}
	
	@Override
	public boolean keepResourcePool() {
		return true;
	}
	
	@Override
	public void onDisposed() {
		System.out.println("OnDisposed");
		sndPlayTime = 0;
		gameOver = false;
		//isPaused = false;
		((CameraGame)mainCamera).resetPosition();
		thePlayer.resetPosition();
		obstacles.resetStat();
		elements.clear();
		elements.add(thePlayer);
		elements.add(mainCamera);
		elements.add(guiHealth);
	}
	
	@Override
	public void preloadResources(ResourcePool pool) {
		overScene.preloadResources(pool);
		
		//BGM
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH +"sounds/game/bgm0.wav"), SND_BGM0);
		
		//各种声音
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/game/gunfire.wav"), SND_GUNFIRE);
		
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
		
		paths = new String[18];
		for(int i = 0; i < 18; ++i) {
			paths[i] = Type24.ASSETS_PATH + "textures/game/obstacles/" + i + ".png";
		}
		TEX_OBSTACLES = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "obstacle");
		
		obstacles.addTemplate(new ObstacleTemplate(this, 120, 40, 15, 10, TEX_OBSTACLES[0], true).setRenderProps(-54, 0, 190, 48));
		obstacles.addTemplate(new ObstacleTemplate(this, 32, 51, 15, 10, TEX_OBSTACLES[1], true).setRenderProps(-33, 0, 66, 52));
		obstacles.addTemplate(new ObstacleTemplate(this, 67, 44, 15, 10, TEX_OBSTACLES[2], true).setRenderProps(-42, 0, 107, 46));
		obstacles.addTemplate(new ObstacleTemplate(this, 35, 55, 15, 10, TEX_OBSTACLES[3], true).setRenderProps(-29, 0, 64, 55));
		obstacles.addTemplate(new ObstacleTemplate(this, 56, 50, 15, 10, TEX_OBSTACLES[4], true).setRenderProps(-32, 0, 88, 50));
		obstacles.addTemplate(new ObstacleTemplate(this, 38, 58, 15, 10, TEX_OBSTACLES[5], true).setRenderProps(-30, 0, 68, 58));
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 54, 15, 10, TEX_OBSTACLES[6], true).setRenderProps(-30, 2, 104, 62));
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 78, 15, 10, TEX_OBSTACLES[7], true).setRenderProps(-38, 0, 112, 78));
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 78, 15, 10, TEX_OBSTACLES[7], true).setRenderProps(-38, 0, 112, 78));
		obstacles.addTemplate(new ObstacleTemplate(this, 90, 94, 15, 10, TEX_OBSTACLES[8], true).setRenderProps(-44, 0, 134, 94));
		obstacles.addTemplate(new ObstacleTemplate(this, 94, 88, 15, 10, TEX_OBSTACLES[9], true).setRenderProps(-38, 0, 132, 88));
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 72, 15, 10, TEX_OBSTACLES[10], true).setRenderProps(-38, 0, 148, 72));
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 74, 15, 10, TEX_OBSTACLES[11], true).setRenderProps(-36, 0, 146, 74));
		obstacles.addTemplate(new ObstacleTemplate(this, 48, 39, 15, 10, TEX_OBSTACLES[12], true).setRenderProps(-25, 0, 73, 39));
		obstacles.addTemplate(new ObstacleTemplate(this, 96, 56, 15, 10, TEX_OBSTACLES[13], true).setRenderProps(-34, 0, 130, 56));
		obstacles.addTemplate(new ObstacleTemplate(this, 34, 26, 15, 10, TEX_OBSTACLES[14], false).setRenderProps(-14, 0, 48, 26));
		obstacles.addTemplate(new ObstacleTemplate(this, 50, 24, 15, 10, TEX_OBSTACLES[15], false));
		obstacles.addTemplate(new ObstacleTemplate(this, 64, 27, 15, 10, TEX_OBSTACLES[16], false));
		obstacles.addTemplate(new ObstacleTemplate(this, 43, 39, 15, 10, TEX_OBSTACLES[17], false));
	}

}
