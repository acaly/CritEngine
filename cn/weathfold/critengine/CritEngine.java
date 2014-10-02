/**
 * 
 */
package cn.weathfold.critengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import cn.weathfold.critengine.scene.Scene;

/**
 * ����ĵ�����ڡ����д���Ĵ�������Ϣѭ���Ľӹ�
 * @author WeAthFolD
 */
public class CritEngine {
	
	private static Scene currentScene = null; //��ǰ�����Scene
	private static GameTimer timer;
	private static float aspectRatio = 1.0F;
	
	/**
	 * ��ĳһScene��ʼ�����壬����ʼ��Ϸѭ����
	 * �ͻ��˳���Ա���������ú�Display���������ԡ�
	 * @param dm ��ʾģʽ
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
	 * ��ȡȫ�ֵ�����ʱ��
	 */
	public static long getVirtualTime() {
		return timer.getTime();
	}
	
	/**
	 * ��ȡȫ�ּ�ʱ��
	 * @return
	 */
	public static GameTimer getTimer() {
		return timer;
	}
	
	/**
	 * �л�����
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
	 * ��ȡ��ǰ�ĳ���
	 * @return
	 */
	public static Scene getCurrentScene() {
		return currentScene;
	}
	
	/**
	 * ÿ֡���¡�
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
