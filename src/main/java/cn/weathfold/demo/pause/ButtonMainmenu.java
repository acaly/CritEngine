package cn.weathfold.demo.pause;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.mainmenu.SceneMainMenu;

/**
 * 回到主界面按钮
 * @author WeAthFolD
 */
public class ButtonMainmenu extends T24Button {
	
	public ButtonMainmenu(Scene scene) {
		super(scene, 211.0D, 231.0D, 400.0D, 54.0D, 
				ScenePause.TEX_MAINMENU0, ScenePause.TEX_MAINMENU1);
	}

	@Override
	public void onButtonReleased() {
		CritEngine.switchScene(SceneMainMenu.INSTANCE);
		super.onButtonReleased();
	}
	
}