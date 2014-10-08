package cn.weathfold.demo.game;

import org.lwjgl.input.Keyboard;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
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
import cn.weathfold.demo.game.enemy.TemplateTraceur;
import cn.weathfold.demo.game.gui.GUIHealth;
import cn.weathfold.demo.game.gui.InfoAmmo;
import cn.weathfold.demo.game.gui.InfoScore;
import cn.weathfold.demo.game.misc.EntityAmmo;
import cn.weathfold.demo.game.misc.EntityMedkit;
import cn.weathfold.demo.game.obstacle.ObstacleFactory;
import cn.weathfold.demo.game.obstacle.ObstacleTemplate;
import cn.weathfold.demo.game.player.EntityPlayer;
import cn.weathfold.demo.over.SceneGameOver;
import cn.weathfold.demo.pause.ScenePause;

/**
 * 游戏的主要界面。
 * @author WeAthFolD
 */
public class SceneGame extends Scene {
	
	public static SceneGame INSTANCE;
	
	//Sounds
	static final int[] BGM_LENGTHS = { 60000, 46000, 72000 };
	public static String 
		SND_GUNFIRE = "gunfire",
		SND_ENEMYFIRE = "enemyfire",
		SND_AMMO = "ammo",
		SND_MEDKIT = "medkit";
	public static String[] SND_BGMS;
	
	//Textures
	public static final String 
		TEX_BACKGROUND = "back",
		TEX_MIDGROUND = "mid",
		TEX_FOREGROUND = "fore",
		TEX_PLAYER_SHADOW = "pshadow",
		TEX_DOGE = "doge",
		TEX_EDGE = "edge",
		TEX_HPBAR = "hpbar",
		TEX_MEDKIT = "medkit",
		TEX_AMMO = "ammo";
	public static String[] 
		TEX_PLAYER_FIRE,
		TEX_PLAYER,
		TEX_HP,
		TEX_OBSTACLES,
		TEX_BULLET,
		TEX_ENEMY,
		TEX_ENEMY_DEAD;
	public LoopAnimation 
		animNormal,
		animShooting;
	
	//Constants
	public static double SCENE_HEIGHT = 285.0D;
	
	//Status
	public EntityPlayer thePlayer;
	public boolean gameOver = false;
	public boolean isPaused = false;
	public double currentScore;
	
	private SceneGameOver overScene = new SceneGameOver(this);
	private ScenePause pauseScene = new ScenePause(this);
	private CameraGameGUI cameraGUI;
	private ObstacleFactory obstacles;
	private GUIHealth guiHealth;
	private int sndLength;
	private long sndPlayTime;
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
			if ((!SceneGame.this.gameOver) && (!SceneGame.this.isPaused))
				SceneGame.this.pause();
			else if (SceneGame.this.isPaused)
				SceneGame.this.isPaused = false;
		}
	};

	public SceneGame() {
		this.mainCamera = new CameraGame(this);

		this.thePlayer = new EntityPlayer(this);
		this.guiHealth = new GUIHealth(this);

		this.cameraGUI = new CameraGameGUI(this);
		this.obstacles = new ObstacleFactory(this);

		onSwitchedScene();
		
		if (INSTANCE == null)
			INSTANCE = this;
	}

	@Override
	public void frameUpdate() {
		long time = CritEngine.getVirtualTime();
		this.keyListener.frameUpdate();

		if (this.gameOver) { 
			this.overScene.frameUpdate();
			return;
		}
		if (this.isPaused) {
			this.pauseScene.frameUpdate();
			return;
		}

		if ((this.sndPlayTime == 0L) || (time - this.sndPlayTime > this.sndLength)) {
			this.sndPlayTime = time;
			int id = RNG.nextInt(3);
			this.sndLength = BGM_LENGTHS[id];
			CESoundEngine.playGlobalSound(SND_BGMS[id], new SoundAttributes(
					BGM_LENGTHS[id]).setGain(0.8F));
		}
		this.obstacles.generateTo(this.mainCamera.getX());
	}

	@Override
	public boolean doesUpdate(Entity ent) {
		if ((this.gameOver) || (this.isPaused))
			return false;
		double EXPAND = 200.0D;
		Rect rt = ((CameraGame) this.mainCamera).getGeomProps();
		rt.move(-EXPAND, 0);
		rt.setWidth(rt.getWidth() + EXPAND * 2.0D);
		boolean b = rt.intersects(ent.getGeomProps());
		rt.move(EXPAND, 0);
		rt.setWidth(rt.getWidth() - EXPAND * 2.0D);
		return b;
	}

	public void gameOver() {
		this.gameOver = true;
		this.overScene.onSwitchedScene();
	}

	protected void pause() {
		this.isPaused = true;
		this.pauseScene.onSwitchedScene();
	}

	@Override
	public void renderBackground() {
		double cameraX = this.mainCamera.getGeomProps().getMinX() / 1035.0D;
		double scale = 0.7998046875D;
		double uFore = cameraX;
		double uMid = cameraX * 0.2D;
		double uBack = cameraX * 0.03D;

		CERenderEngine.bindTexture("back");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uBack,
				0.0D, uBack + scale, 1.0D);

		CERenderEngine.bindTexture("mid");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uMid, 0.0D,
				uMid + scale, 1.0D);

		CERenderEngine.bindTexture("fore");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uFore,
				0.0D, uFore + scale, 1.0D);
	}

	@Override
	public void renderForeground() {
		super.renderForeground();
		this.cameraGUI.draw();

		if (this.gameOver) {
			this.overScene.renderBackground();
			this.overScene.renderForeground();
		} else if (this.isPaused) {
			this.pauseScene.renderBackground();
			this.pauseScene.renderForeground();
		}
	}

	@Override
	public boolean keepResourcePool() {
		return true;
	}

	public int calculateScore() {
		if (mainCamera == null) //迷の空指针
			return 0;
		return (int) currentScore + 
				(int) (0.314D * (-512.0D - this.mainCamera.getGeomProps().getMinX()));
	}

	/**
	 * 加分
	 * @param i
	 */
	public void score(int i) {
		currentScore += i;
	}

	@Override
	public void onSwitchedScene() {
		this.sndPlayTime = 0L;
		this.gameOver = false;
		this.isPaused = false;
		((CameraGame) this.mainCamera).resetPosition();
		this.thePlayer.resetPosition();
		this.obstacles.resetStat();
		this.elements.clear();
		this.elements.add(this.thePlayer);
		this.elements.add(this.mainCamera);
		this.elements.add(this.guiHealth);
		this.elements.add(new InfoAmmo(this));
		elements.add(new InfoScore(this));
		this.currentScore = 0;
	}

	@Override
	public void preloadResources(ResourcePool pool) {
		//子场景
		this.overScene.preloadResources(pool);
		this.pauseScene.preloadResources(pool);
		String[] paths = new String[3];

		//声音
		for (int i = 0; i < 3; i++) {
			paths[i] = (Type24.ASSETS_PATH + "sounds/game/bgm" + i + ".wav");
		}
		SND_BGMS = pool.preloadSoundArray(WavSoundObject.readArray(paths), "bgm");
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/game/gunfire.wav"), SND_GUNFIRE);
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/game/enemyfire.wav"), SND_ENEMYFIRE);
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/game/ammopickup.wav"), SND_AMMO);
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/game/medkit.wav"), SND_MEDKIT);
		
		//贴图
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/back.png"), TEX_BACKGROUND);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/middle.png"), TEX_MIDGROUND);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/front.png"), TEX_FOREGROUND);
		
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/doge.png"), TEX_DOGE);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/player_shadow.png"), TEX_PLAYER_SHADOW);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/" + (Type24.DEBUG ? "edge.png" : "blank.png")), TEX_EDGE);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/ammo.png"), TEX_AMMO);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/medkit.png"), TEX_MEDKIT);

		//动画注册
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/game/hpbar.png"), TEX_HPBAR);
		paths = new String[4];
		for (int i = 0; i < 4; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/hp_" + i + ".png");
		}
		TEX_HP = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "hp");

		paths = new String[18];
		for (int i = 0; i < 18; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/obstacles/" + i + ".png");
		}
		TEX_OBSTACLES = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "obstacle");

		paths = new String[1];
		for (int i = 0; i < 1; i++) {
			paths[i] = Type24.ASSETS_PATH + "textures/game/bullet.png";
		}
		TEX_BULLET = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "bullet");

		paths = new String[7];
		for (int i = 0; i < 7; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/enemy" + i + ".png");
		}
		TEX_ENEMY = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "enemy");

		paths = new String[5];
		for (int i = 0; i < 5; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/enemy_dead" + i + ".png");
		}
		TEX_ENEMY_DEAD = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "edead");
		
		paths = new String[2];
		for (int i = 0; i < 2; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/player_fire" + i + ".png");
		}
		TEX_PLAYER_FIRE = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "player_fire");

		paths = new String[4];
		for (int i = 0; i < 4; i++) {
			paths[i] = (Type24.ASSETS_PATH + "textures/game/player" + i + ".png");
		}
		TEX_PLAYER = pool.preloadTextureArray(PNGTextureObject.readArray(paths), "player");

		Rect drawBound = new Rect(0.0D, 0.0D, 180.0D, 180.0D);
		this.animNormal = new RandomAnimation(TEX_PLAYER).setDrawingQuad(drawBound);
		this.animShooting = new LoopAnimation(TEX_PLAYER_FIRE).setDrawingQuad(drawBound);

		//生成模板注册
		obstacles.addTemplate(new ObstacleTemplate(this, 120, 40, 15, 6,
				TEX_OBSTACLES[0], true).setRenderProps(-54, 0, 190, 48), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 32, 51, 15, 3,
				TEX_OBSTACLES[1], true).setRenderProps(-33, 0, 66, 52), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 100.5, 66, 15, 15,
				TEX_OBSTACLES[2], true).setRenderProps(-63, 0, 160.5F, 69), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 52.5, 82.5, 15, 10,
				TEX_OBSTACLES[3], true).setRenderProps(-39, 0, 96, 82.5F), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 84, 75, 40, 18,
				TEX_OBSTACLES[4], true).setRenderProps(-48, 0, 132, 75), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 38, 58, 15, 4,
				TEX_OBSTACLES[5], true).setRenderProps(-30, 0, 68, 58), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 54, 15, 5,
				TEX_OBSTACLES[6], true).setRenderProps(-30, 2, 104, 62), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 78, 15, 6,
				TEX_OBSTACLES[7], true).setRenderProps(-38, 0, 112, 78), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 90, 94, 15, 7,
				TEX_OBSTACLES[8], true).setRenderProps(-44, 0, 134, 94), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 94, 88, 15, 8,
				TEX_OBSTACLES[9], true).setRenderProps(-38, 0, 132, 88), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 72, 15, 9,
				TEX_OBSTACLES[10], true).setRenderProps(-38, 0, 148, 72), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 74, 15, 10,
				TEX_OBSTACLES[11], true).setRenderProps(-36, 0, 146, 74), 0.2);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 72, 58.5, 15, 4,
				TEX_OBSTACLES[12], true).setRenderProps(-37.5F, 0, 109.5F, 58.5F), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 96, 56, 15, 10,
				TEX_OBSTACLES[13], true).setRenderProps(-34, 0, 130, 56).setAttackAllSide(), 1.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 68, 52, 15, 10,
				TEX_OBSTACLES[14], false).setRenderProps(-28, 0, 96, 52), 2.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 50, 24, 15, 10,
				TEX_OBSTACLES[15], false), 2.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 102.4, 43.2, 15, 10,
				TEX_OBSTACLES[16], false), 2.0);
		
		obstacles.addTemplate(new ObstacleTemplate(this, 64.5, 58.5, 15, 10,
				TEX_OBSTACLES[17], false), 2.0);
		
		this.obstacles.addTemplate(new TemplateTraceur(this), 10.0);
		this.obstacles.addTemplate(new EntityAmmo.Template(this), 5.0);
		this.obstacles.addTemplate(new EntityMedkit.Template(this), 5.0);
	}
}