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
import cn.weathfold.demo.game.misc.EntityAmmo;
import cn.weathfold.demo.game.misc.EntityMedkit;
import cn.weathfold.demo.game.obstacle.ObstacleFactory;
import cn.weathfold.demo.game.obstacle.ObstacleTemplate;
import cn.weathfold.demo.game.player.EntityPlayer;
import cn.weathfold.demo.over.SceneGameOver;
import cn.weathfold.demo.pause.ScenePause;

public class SceneGame extends Scene {
	public static SceneGame INSTANCE;
	public static String[] SND_BGMS;
	public static String SND_GUNFIRE = "gunfire";
	public static String SND_ENEMYFIRE = "enemyfire";
	public static String SND_AMMO = "ammo";
	public static String SND_MEDKIT = "medkit";

	static final int[] BGM_LENGTHS = { 60000, 46000, 72000 };
	public static final String TEX_BACKGROUND = "back";
	public static final String TEX_MIDGROUND = "mid";
	public static final String TEX_FOREGROUND = "fore";
	public static final String TEX_PLAYER_SHADOW = "pshadow";
	public static final String TEX_DOGE = "doge";
	public static final String TEX_EDGE = "edge";
	public static final String TEX_HPBAR = "hpbar";
	public static final String TEX_MEDKIT = "medkit";
	public static final String TEX_AMMO = "ammo";
	public static String[] TEX_PLAYER_FIRE;
	public static String[] TEX_PLAYER;
	public static String[] TEX_HP;
	public static String[] TEX_OBSTACLES;
	public static String[] TEX_BULLET;
	public static String[] TEX_ENEMY;
	public static String[] TEX_ENEMY_DEAD;
	public LoopAnimation animNormal;
	public LoopAnimation animShooting;
	public static double SCENE_HEIGHT = 285.0D;
	private long sndPlayTime;
	private int sndLength;
	public int currentScore;
	public EntityPlayer thePlayer;
	private CameraGameGUI cameraGUI;
	private ObstacleFactory obstacles;
	private SceneGameOver overScene = new SceneGameOver(this);
	private ScenePause pauseScene = new ScenePause(this);
	private GUIHealth guiHealth;
	public boolean gameOver = false;
	public boolean isPaused = false;
	private KeyEventProducer keyListener = new KeyEventProducer() {
		{
			this.addKeyListening(Keyboard.KEY_ESCAPE);
		}
		
		public void onKeyDown(int kid) {
		}

		public void onKeyFrame(int kid) {
		}

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

	public void frameUpdate() {
		long time = CritEngine.getVirtualTime();
		this.currentScore = ((int) (0.314D * (-512.0D - this.mainCamera
				.getGeomProps().pos.x)));
		this.keyListener.frameUpdate();

		if (this.gameOver) {
			this.overScene.frameUpdate();
			return;
		}
		if (this.isPaused) {
			this.pauseScene.frameUpdate();
			return;
		}

		if ((this.sndPlayTime == 0L)
				|| (time - this.sndPlayTime > this.sndLength)) {
			this.sndPlayTime = time;
			int id = RNG.nextInt(3);
			this.sndLength = BGM_LENGTHS[id];
			CESoundEngine.playGlobalSound(SND_BGMS[id], new SoundAttributes(
					BGM_LENGTHS[id]).setGain(0.8F));
		}
		this.obstacles.generateTo(this.mainCamera.getX());
	}

	public boolean doesUpdate(Entity ent) {
		if ((this.gameOver) || (this.isPaused))
			return false;
		double EXPAND = 200.0D;
		Rect rt = ((CameraGame)this.mainCamera).gp();
		rt.pos.x -= EXPAND;
		rt.width += EXPAND * 2.0D;
		boolean b = rt.intersects(ent.getGeomProps());
		rt.pos.x += EXPAND;
		rt.width -= EXPAND * 2.0D;
		return b;
	}

	public void gameOver() {
		this.gameOver = true;
	}

	public void pause() {
		this.isPaused = true;
	}

	public void renderBackground() {
		double cameraX = this.mainCamera.getGeomProps().getMinX() / 1035.0D;
		double scale = 0.7998046875D;
		double uFore = cameraX;
		double uMid = cameraX * 0.2D;
		double uBack = cameraX * 0.05D;

		CERenderEngine.bindTexture("back");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uBack,
				0.0D, uBack + 0.7998046875D, 1.0D);

		CERenderEngine.bindTexture("mid");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uMid, 0.0D,
				uMid + 0.7998046875D, 1.0D);

		CERenderEngine.bindTexture("fore");
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D, uFore,
				0.0D, uFore + 0.7998046875D, 1.0D);
	}

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

	public boolean keepResourcePool() {
		return true;
	}

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
		this.currentScore = 0;
	}

	public void preloadResources(ResourcePool pool) {
		this.overScene.preloadResources(pool);
		this.pauseScene.preloadResources(pool);

		String[] paths = new String[3];
		for (int i = 0; i < 3; i++) {
			paths[i] = ("/assets/type24/sounds/game/bgm" + i + ".wav");
		}
		SND_BGMS = pool.preloadSoundArray(WavSoundObject.readArray(paths),
				"bgm");

		pool.preloadSound(new WavSoundObject(
				"/assets/type24/sounds/game/gunfire.wav"), SND_GUNFIRE);
		pool.preloadSound(new WavSoundObject(
				"/assets/type24/sounds/game/enemyfire.wav"), SND_ENEMYFIRE);
		pool.preloadSound(new WavSoundObject(
				"/assets/type24/sounds/game/ammopickup.wav"), SND_AMMO);
		pool.preloadSound(new WavSoundObject(
				"/assets/type24/sounds/game/medkit.wav"), SND_MEDKIT);

		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/back.png"), "back");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/middle.png"), "mid");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/front.png"), "fore");

		paths = new String[2];
		for (int i = 0; i < 2; i++) {
			paths[i] = ("/assets/type24/textures/game/player_fire" + i + ".png");
		}
		TEX_PLAYER_FIRE = pool.preloadTextureArray(
				PNGTextureObject.readArray(paths), "player_fire");

		paths = new String[4];
		for (int i = 0; i < 4; i++) {
			paths[i] = ("/assets/type24/textures/game/player" + i + ".png");
		}
		TEX_PLAYER = pool.preloadTextureArray(
				PNGTextureObject.readArray(paths), "player");

		Rect drawBound = new Rect(0.0D, 0.0D, 180.0D, 180.0D);
		this.animNormal = new RandomAnimation(TEX_PLAYER)
				.setDrawingQuad(drawBound);
		this.animShooting = new LoopAnimation(TEX_PLAYER_FIRE)
				.setDrawingQuad(drawBound);

		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/doge.png"), "doge");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/player_shadow.png"), "pshadow");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/" + (!Type24.DEBUG ? "blank.png" : "edge.png")), "edge");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/ammo.png"), "ammo");
		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/medkit.png"), "medkit");

		pool.preloadTexture(new PNGTextureObject(
				"/assets/type24/textures/game/hpbar.png"), "hpbar");
		paths = new String[4];
		for (int i = 0; i < 4; i++) {
			paths[i] = ("/assets/type24/textures/game/hp_" + i + ".png");
		}
		TEX_HP = pool.preloadTextureArray(PNGTextureObject.readArray(paths),
				"hp");

		paths = new String[18];
		for (int i = 0; i < 18; i++) {
			paths[i] = ("/assets/type24/textures/game/obstacles/" + i + ".png");
		}
		TEX_OBSTACLES = pool.preloadTextureArray(
				PNGTextureObject.readArray(paths), "obstacle");

		paths = new String[1];
		for (int i = 0; i < 1; i++) {
			paths[i] = "/assets/type24/textures/game/bullet.png";
		}
		TEX_BULLET = pool.preloadTextureArray(
				PNGTextureObject.readArray(paths), "bullet");

		paths = new String[7];
		for (int i = 0; i < 7; i++) {
			paths[i] = ("/assets/type24/textures/game/enemy" + i + ".png");
		}
		TEX_ENEMY = pool.preloadTextureArray(PNGTextureObject.readArray(paths),
				"enemy");

		paths = new String[5];
		for (int i = 0; i < 5; i++) {
			paths[i] = ("/assets/type24/textures/game/enemy_dead" + i + ".png");
		}
		TEX_ENEMY_DEAD = pool.preloadTextureArray(
				PNGTextureObject.readArray(paths), "edead");

		/*
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 120.0D, 40.0D,
		 * 15.0D, 10, TEX_OBSTACLES[0], true).setRenderProps(-54.0F, 0.0F,
		 * 190.0F, 48.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 32.0D, 51.0D, 15.0D, 10, TEX_OBSTACLES[1],
		 * true).setRenderProps(-33.0F, 0.0F, 66.0F, 52.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 67.0D, 44.0D,
		 * 15.0D, 10, TEX_OBSTACLES[2], true).setRenderProps(-42.0F, 0.0F,
		 * 107.0F, 46.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 35.0D, 55.0D, 15.0D, 10, TEX_OBSTACLES[3],
		 * true).setRenderProps(-29.0F, 0.0F, 64.0F, 55.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 56.0D, 50.0D,
		 * 15.0D, 10, TEX_OBSTACLES[4], true).setRenderProps(-32.0F, 0.0F,
		 * 88.0F, 50.0F)); this.obstacles.addTemplate(new ObstacleTemplate(this,
		 * 38.0D, 58.0D, 15.0D, 10, TEX_OBSTACLES[5],
		 * true).setRenderProps(-30.0F, 0.0F, 68.0F, 58.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 74.0D, 54.0D,
		 * 15.0D, 10, TEX_OBSTACLES[6], true).setRenderProps(-30.0F, 2.0F,
		 * 104.0F, 62.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 74.0D, 78.0D, 15.0D, 10, TEX_OBSTACLES[7],
		 * true).setRenderProps(-38.0F, 0.0F, 112.0F, 78.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 74.0D, 78.0D,
		 * 15.0D, 10, TEX_OBSTACLES[7], true).setRenderProps(-38.0F, 0.0F,
		 * 112.0F, 78.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 90.0D, 94.0D, 15.0D, 10, TEX_OBSTACLES[8],
		 * true).setRenderProps(-44.0F, 0.0F, 134.0F, 94.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 94.0D, 88.0D,
		 * 15.0D, 10, TEX_OBSTACLES[9], true).setRenderProps(-38.0F, 0.0F,
		 * 132.0F, 88.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 110.0D, 72.0D, 15.0D, 10, TEX_OBSTACLES[10],
		 * true).setRenderProps(-38.0F, 0.0F, 148.0F, 72.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 110.0D, 74.0D,
		 * 15.0D, 10, TEX_OBSTACLES[11], true).setRenderProps(-36.0F, 0.0F,
		 * 146.0F, 74.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 48.0D, 39.0D, 15.0D, 10, TEX_OBSTACLES[12],
		 * true).setRenderProps(-25.0F, 0.0F, 73.0F, 39.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 96.0D, 56.0D,
		 * 15.0D, 10, TEX_OBSTACLES[13], true).setRenderProps(-34.0F, 0.0F,
		 * 130.0F, 56.0F)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 34.0D, 26.0D, 15.0D, 10, TEX_OBSTACLES[14],
		 * false).setRenderProps(-14.0F, 0.0F, 48.0F, 26.0F));
		 * this.obstacles.addTemplate(new ObstacleTemplate(this, 50.0D, 24.0D,
		 * 15.0D, 10, TEX_OBSTACLES[15], false)); this.obstacles.addTemplate(new
		 * ObstacleTemplate(this, 64.0D, 27.0D, 15.0D, 10, TEX_OBSTACLES[16],
		 * false)); this.obstacles.addTemplate(new ObstacleTemplate(this, 43.0D,
		 * 39.0D, 15.0D, 10, TEX_OBSTACLES[17], false));
		 */
/*
		obstacles.addTemplate(new ObstacleTemplate(this, 120, 40, 15, 10,
				TEX_OBSTACLES[0], true).setRenderProps(-54, 0, 190, 48), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 32, 51, 15, 10,
				TEX_OBSTACLES[1], true).setRenderProps(-33, 0, 66, 52), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 67, 44, 15, 10,
				TEX_OBSTACLES[2], true).setRenderProps(-42, 0, 107, 46), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 35, 55, 15, 10,
				TEX_OBSTACLES[3], true).setRenderProps(-29, 0, 64, 55), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 56, 50, 15, 10,
				TEX_OBSTACLES[4], true).setRenderProps(-32, 0, 88, 50), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 38, 58, 15, 10,
				TEX_OBSTACLES[5], true).setRenderProps(-30, 0, 68, 58), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 54, 15, 10,
				TEX_OBSTACLES[6], true).setRenderProps(-30, 2, 104, 62), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 78, 15, 10,
				TEX_OBSTACLES[7], true).setRenderProps(-38, 0, 112, 78), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 74, 78, 15, 10,
				TEX_OBSTACLES[7], true).setRenderProps(-38, 0, 112, 78), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 90, 94, 15, 10,
				TEX_OBSTACLES[8], true).setRenderProps(-44, 0, 134, 94), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 94, 88, 15, 10,
				TEX_OBSTACLES[9], true).setRenderProps(-38, 0, 132, 88), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 72, 15, 10,
				TEX_OBSTACLES[10], true).setRenderProps(-38, 0, 148, 72), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 110, 74, 15, 10,
				TEX_OBSTACLES[11], true).setRenderProps(-36, 0, 146, 74), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 48, 39, 15, 10,
				TEX_OBSTACLES[12], true).setRenderProps(-25, 0, 73, 39), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 96, 56, 15, 10,
				TEX_OBSTACLES[13], true).setRenderProps(-34, 0, 130, 56), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 34, 26, 15, 10,
				TEX_OBSTACLES[14], false).setRenderProps(-14, 0, 48, 26), 1.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 50, 24, 15, 10,
				TEX_OBSTACLES[15], false), 6.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 64, 27, 15, 10,
				TEX_OBSTACLES[16], false), 6.0);
		obstacles.addTemplate(new ObstacleTemplate(this, 43, 39, 15, 10,
				TEX_OBSTACLES[17], false), 6.0);
		*/

		this.obstacles.addTemplate(new TemplateTraceur(this), 1F);
		this.obstacles.addTemplate(new EntityAmmo.Template(this), 1F);
		this.obstacles.addTemplate(new EntityMedkit.Template(this), 1F);
	}
}