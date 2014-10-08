package cn.weathfold.demo;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.demo.mainmenu.SceneMainMenu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Demo的主体，也是入口
 * @author WeAthFolD
 */
public class Type24 {
	
	/**
	 * -------------操作说明--------------
	 * | W: 向屏幕上方移动
	 * | S: 向屏幕下方移动
	 * | SPACE: 跳跃
	 * | 鼠标左键：射击（在有弹药时）
	 * |
	 * -------------游戏目标--------------
	 * | 躲避障碍物，收集血包和弹药，打倒敌人和往前走，获得尽量高的分数。
	 * | 游戏长度是无限的。如果能坚持到玩家坐标下溢会有特殊奖励哦（笑）
	 * |
	 * |------------规则-----------------
	 * | 碰到障碍物或被敌人射击会扣血，如果血量降为0，或者被障碍物和敌人挤出世界，则游戏结束
	 * | 看看你能得多少分吧~
	 * | 佩戴耳机食用效果更佳哦~
	 * |--------------------------------
	 */
	
	public static final String ASSETS_PATH = "/assets/type24/";
	//全局声音
	public static final String 
		SND_BUTTON_CLICK = "btnclick",
		SND_BUTTON_RELEASE = "btnrelease";
	public static final boolean DEBUG = false; //是否处于调试模式

	/**
	 * 入口函数
	 */
	public static void main(String[] args) {
		new Type24().run();
	}

	/**
	 * 走你！~\(≧▽≦)/~
	 */
	public void run() {
		Display.setTitle("CritEngine : Type 24");
		try {
			Display.setDisplayMode(new DisplayMode(819, 512));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		CERenderEngine.setLoadFontSize(64);
		CritEngine.setEnforceFPS(60);
		CritEngine.start(new SceneMainMenu());
	}
	
}