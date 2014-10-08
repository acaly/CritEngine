package cn.weathfold.demo.mainmenu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.game.SceneGame;

/**
 * 开始按钮
 * @author WeAthFolD
 */
public class ButtonStart extends T24Button {

	public ButtonStart(Scene scene) {
		super(scene, 10.0D, 52.0D, 220.0D, 40.0D, 
				SceneMainMenu.TEX_START, SceneMainMenu.TEX_START_ACTIVATED);
	}

	@Override
	public void onButtonReleased() {
		CESoundEngine.refresh();

		super.onButtonReleased();
		CritEngine.switchScene(SceneGame.INSTANCE == null ? 
				new SceneGame() : SceneGame.INSTANCE);
	}
}