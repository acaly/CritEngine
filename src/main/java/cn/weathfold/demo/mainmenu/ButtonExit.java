package cn.weathfold.demo.mainmenu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;

/**
 * 退出按钮
 * @author WeAthFolD
 */
public class ButtonExit extends T24Button {

	public ButtonExit(Scene scene) {
		super(scene, 10.0D, 12.0D, 220.0D, 40.0D, 
				SceneMainMenu.TEX_QUIT, SceneMainMenu.TEX_QUIT_ACTIVATED);
	}

	@Override
	public void onButtonFrame() {
	}

	@Override
	public void onButtonReleased() {
		CritEngine.switchScene(null); //愉快的退出
	}
}