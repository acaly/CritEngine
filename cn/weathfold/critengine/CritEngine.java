/**
 * 
 */
package cn.weathfold.critengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import cn.weathfold.critengine.scene.Scene;

/**
 * 引擎的调用入口。进行窗体的创建和消息循环的接管
 * @author WeAthFolD
 */
public class CritEngine {
	
	private static Scene currentScene = null; //当前窗体的Scene
	private static GameTimer timer;
	private static float aspectRatio = 1.0F;
	
	/**
	 * 以某一Scene初始化窗体，并开始游戏循环。
	 * 客户端程序员有责任设置好Display的其他属性。
	 * @param dm 显示模式
	 * @param sc
	 */
	public static void start(Scene sc) {
		try {
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		
		setAspectRatio((float)Display.getWidth() / Display.getHeight());
		timer = new GameTimer();
		
		switchScene(sc);
		CEDebugger.fine("Created view window successfully, with scene " + sc);
		
		while(!Display.isCloseRequested()) {
			timer.updateTime();
			updateCycle();
			Display.update();
		}
		
		Display.destroy();
	}
	
	public static void updateDisplayInfo() {
		setAspectRatio(Display.getWidth() / Display.getHeight());
	}
	
	/**
	 * 获取全局的虚拟时间
	 */
	public static long getVirtualTime() {
		return timer.getTime();
	}
	
	/**
	 * 获取全局计时器
	 * @return
	 */
	public static GameTimer getTimer() {
		return timer;
	}
	
	/**
	 * 切换场景
	 * @param another
	 * @return
	 */
	public static void switchScene(Scene another) {
		if(currentScene != null) {
			disposeCurrentScene();
		}
		loadScene(another);
	}

	/**
	 * 获取当前的场景
	 * @return
	 */
	public static Scene getCurrentScene() {
		return currentScene;
	}
	
	/**
	 * 每帧更新。
	 */
	private static void updateCycle() {
		
	}
	
	private static void disposeCurrentScene() {
		
	}
	
	private static void loadScene(Scene sc) {
		
	}

	public static float getAspectRatio() {
		return aspectRatio;
	}

	private static void setAspectRatio(float aspectRatio) {
		CEDebugger.fine("Screen Aspect Ratio updated to " + aspectRatio);
		CritEngine.aspectRatio = aspectRatio;
	}
}
