/**
 * 
 */
package cn.weathfold.critengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;

/**
 * 引擎的调用入口。进行窗体的初始化、创建，以及Scene的切换。
 * @author WeAthFolD
 */
public class CritEngine {

	private static Scene currentScene = null; // 当前窗体的Scene

	private static GameTimer timer;
	private static float aspectRatio = 1.0F;
	private static int ENFORCE_FPS_RATE = 60;

	/**
	 * 以某一Scene初始化窗体，并开始游戏循环。 客户端程序员有责任设置好Display的其他属性。
	 * 
	 * @param dm
	 *            显示模式
	 * @param sc
	 */
	public static void start(Scene sc) {
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		setAspectRatio((float) Display.getWidth() / Display.getHeight());
		timer = new GameTimer();

		initGLProps();
		CESoundEngine.init();

		switchScene(sc);
		CEDebugger.fine("Created view window successfully, with scene " + sc);
		updateDisplayInfo();

		while (!Display.isCloseRequested()) {
			timer.updateTime();
			updateCycle();
			Display.update();
		}

		Display.destroy();
	}

	public static void updateDisplayInfo() {
		setAspectRatio((float)Display.getWidth() / Display.getHeight());
		if(currentScene != null) {
			currentScene.mainCamera.refreshStat();
		}
	}

	private static void initGLProps() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, Display.getWidth(), 0.0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDepthFunc(GL11.GL_ALWAYS);
	}

	/**
	 * 获取全局的虚拟时间
	 */
	public static long getVirtualTime() {
		return timer == null ? (Sys.getTime() * 1000) / Sys.getTimerResolution() : timer.getTime();
	}

	/**
	 * 获取全局计时器
	 * 
	 * @return
	 */
	public static GameTimer getTimer() {
		return timer;
	}
	
	/**
	 * 切换到另外一个Scene
	 * @param another
	 */
	public static void switchScene(Scene another) {
		if (currentScene != null) {
			disposeCurrentScene();
		}
		CEUpdateProcessor.tickState = false;
		loadScene(another);
	}

	/**
	 * 获取当前的场景
	 * 
	 * @return
	 */
	public static Scene getCurrentScene() {
		return currentScene;
	}

	/**
	 * 每帧更新。
	 */
	private static void updateCycle() {
		//强制FPS同步
		Display.sync(ENFORCE_FPS_RATE);
		timer.updateTime();
		
		CEUpdateProcessor.frameUpdate(currentScene);
	}

	private static void disposeCurrentScene() {
		if(!currentScene.keepResourcePool()) {
			CEResourceHandler.freeResourcePool(currentScene);
		}
		currentScene = null;
	}

	private static void loadScene(Scene sc) {
		ResourcePool rp = CEResourceHandler.allocatePool(sc);
		CEDebugger.fine("Loading scene " + sc);
		if(rp != null) {
			sc.preloadResources(rp);
		}
		CEDebugger.fine("Loading scene " + sc + " finished");
		currentScene = sc;
	}

	public static float getAspectRatio() {
		return aspectRatio;
	}

	private static void setAspectRatio(float aspectRatio) {
		CEDebugger.fine("Screen Aspect Ratio updated to " + aspectRatio);
		CritEngine.aspectRatio = aspectRatio;
	}
}
