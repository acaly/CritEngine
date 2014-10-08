package cn.weathfold.demo.pause;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;

/**
 * 退出按钮
 * @author WeAthFolD
 */
public class ButtonQuit extends T24Button {
	
	public ButtonQuit(Scene scene) {
		super(scene, 211.0D, 134.0D, 400.0D, 54.0D, 
				ScenePause.TEX_QUIT0, ScenePause.TEX_QUIT1);
	}

	@Override
	public void onButtonReleased() {
		CritEngine.switchScene(null);
	}
}