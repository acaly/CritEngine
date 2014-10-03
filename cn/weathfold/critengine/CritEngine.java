/**
 * 
 */
package cn.weathfold.critengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.scene.Scene;

/**
 * 引擎的调用入口。进行窗体的创建和消息循环的接管
 * 
 * @author WeAthFolD
 */
public class CritEngine {

	private static Scene currentScene = null; // 当前窗体的Scene

	private static GameTimer timer;
	private static float aspectRatio = 1.0F;

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

		switchScene(sc);
		CEDebugger.fine("Created view window successfully, with scene " + sc);

		while (!Display.isCloseRequested()) {
			timer.updateTime();
			updateCycle();
			Display.update();
		}

		Display.destroy();
	}

	public static void updateDisplayInfo() {
		setAspectRatio(Display.getWidth() / Display.getHeight());
	}

	private static void initGLProps() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, Display.getWidth(), 0.0, Display.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * 获取全局的虚拟时间
	 */
	public static long getVirtualTime() {
		return timer.getTime();
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
	 * 切换场景
	 * 
	 * @param another
	 * @return
	 */
	public static void switchScene(Scene another) {
		if (currentScene != null) {
			disposeCurrentScene();
		}
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
		Display.sync(60);
		
		timer.updateTime();
		currentScene.frameUpdate();
		
		// draw sequence begin
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		// set the color of the quad (R,G,B,A)
		/*
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(100, 100);
		GL11.glVertex2f(100 + 200, 100);
		GL11.glVertex2f(100 + 200, 100 + 200);
		GL11.glVertex2f(100, 100 + 200);
		GL11.glEnd();*/

		if (currentScene != null) {
			currentScene.mainCamera.draw();
		}

		//Display.update();
		// draw sequence end
	}

	private static void disposeCurrentScene() {

	}

	private static void loadScene(Scene sc) {
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
