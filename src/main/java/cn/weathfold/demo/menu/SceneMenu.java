/**
 * 
 */
package cn.weathfold.demo.menu;

import java.util.Random;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.resource.WavSoundObject;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;
import cn.weathfold.demo.Type24;

/**
 * @author WeAthFolD
 *
 */
public class SceneMenu extends Scene {
	
	protected static final String
		SND_BACK = "BGM";
	
	protected static final String 
		TEX_ANIM = "back",
		TEX_START = "start0",
		TEX_START_ACTIVATED = "start1",
		TEX_QUIT = "quit0",
		TEX_QUIT_ACTIVATED = "quit1",
		TEX_TITLE = "title";
	
	private String ANIM_MAIN[];
	private LoopAnimation backAnim;
	private Random rand = new Random();
	private boolean played = false;
	private long lastPlayTick;

	public SceneMenu() {
		this.mainCamera = new Camera(this, 0, 0, 512, 512, Alignment.ALIGN_WIDTH); //设置主摄像机
		elements.add(new EntityTitle(this));
		elements.add(new ButtonStart(this));
		elements.add(new ButtonExit(this));
	}
	
	public void renderScene() {
		//循环播放BGM
		if(CritEngine.getVirtualTime() - lastPlayTick > 92000) {
			played = false;
		}
		
		if(!played) {
			played = true;
			CESoundEngine.playGlobalSound(SND_BACK, new SoundAttributes(92000));
			lastPlayTick = CritEngine.getVirtualTime();
		}
	}

	public void preloadResources(ResourcePool pool) {
		String anim[] = new String[12];
		for(int i = 0; i < 12; ++i) {
			anim[i] = Type24.ASSETS_PATH + "textures/menu/back" + i + ".png";
		}
		ANIM_MAIN = pool.preloadTextureArray(PNGTextureObject.readArray(anim), TEX_ANIM);
		backAnim = new LoopAnimation(ANIM_MAIN) {
			//随机帧
			protected int nextFrame() {
				return rand.nextInt(12);
			}
		}.setFrameInterval(300);
		
		//加载菜单贴图
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/exit.png"), TEX_QUIT);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/exit_activated.png"),
				TEX_QUIT_ACTIVATED);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/start.png"), TEX_START);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + 
				"textures/menu/start_activated.png"), TEX_START_ACTIVATED);
		pool.preloadTexture(new PNGTextureObject(Type24.ASSETS_PATH + "textures/menu/title.png"), TEX_TITLE);
		
		pool.preloadSound(new WavSoundObject(Type24.ASSETS_PATH + "sounds/menu/bgm.wav"), SND_BACK);
	}
	
	public void renderBackground() {
		backAnim.draw();
	}
}
