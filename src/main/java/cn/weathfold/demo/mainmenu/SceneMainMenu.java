package cn.weathfold.demo.mainmenu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.resource.WavSoundObject;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.Type24;

import java.util.Random;

import org.lwjgl.opengl.GL11;

/**
 * 主界面菜单类~
 * @author WeAthFolD
 */
public class SceneMainMenu extends Scene {
	
	public static SceneMainMenu INSTANCE;
	
	protected static final String 
		SND_BACK = "BGM",
		TEX_ANIM = "back",
		TEX_START = "start0",
		TEX_START_ACTIVATED = "start1",
		TEX_QUIT = "quit0",
		TEX_QUIT_ACTIVATED = "quit1",
		TEX_TITLE = "title";
	
	private String[] ANIM_MAIN;
	private LoopAnimation backAnim;
	private Random rand = new Random();
	
	//Props
	private boolean played = false;
	private long lastPlayTick;
	private static boolean firstLoad = true;

	public SceneMainMenu() {
		this.mainCamera = new Camera(this, -153.5D, 0.0D, 819.0D, 512.0D,
				Camera.Alignment.ALIGN_WIDTH);
		this.elements.add(new EntityTitle(this));
		this.elements.add(new ButtonStart(this));
		this.elements.add(new ButtonExit(this));
		if (INSTANCE == null)
			INSTANCE = this;
	}

	@Override
	public void frameUpdate() {
		if (CritEngine.getVirtualTime() - this.lastPlayTick > 92000L) {
			this.played = false;
		}

		if (!this.played) {
			this.played = true;
			CESoundEngine.playGlobalSound("BGM", new SoundAttributes(92000));
			this.lastPlayTick = CritEngine.getVirtualTime();
		}
	}

	@Override
	public void preloadResources(ResourcePool pool) {
		if (firstLoad) {
			//加载全局声音
			CEResourceHandler.globalPreloadSound(
					new WavSoundObject(Type24.ASSETS_PATH + "sounds/buttonclick.wav"), Type24.SND_BUTTON_CLICK);
			CEResourceHandler.globalPreloadSound(
					new WavSoundObject(Type24.ASSETS_PATH + "sounds/buttonclickrelease.wav"), Type24.SND_BUTTON_RELEASE);
			firstLoad = false;
		}

		String[] anim = new String[12];
		for (int i = 0; i < 12; i++) {
			anim[i] = (Type24.ASSETS_PATH + "textures/menu/back" + i + ".png");
		}
		
		this.ANIM_MAIN = pool.preloadTextureArray(PNGTextureObject.readArray(anim), "back");
		//设置背景动画
		this.backAnim = new LoopAnimation(this.ANIM_MAIN) {
			@Override
			protected int nextFrame() {
				return SceneMainMenu.this.rand.nextInt(12);
			}
		}.setFrameInterval(300).setDrawingQuad(new Rect(153.5D, 0.0D, 512.0D, 512.0D));

		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/exit.png"), "quit0");
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/exit_activated.png"), "quit1");
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/start.png"), "start0");
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/start_activated.png"), "start1");
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/title.png"), "title");
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/menu/bgm.wav"), "BGM");
	}

	@Override
	public void onSwitchedScene() {
		this.lastPlayTick = 0L;
	}

	@Override
	public boolean keepResourcePool() {
		return true;
	}

	@Override
	public void renderBackground() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.backAnim.draw();
	}
}