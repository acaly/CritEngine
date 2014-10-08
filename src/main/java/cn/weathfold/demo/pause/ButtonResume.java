package cn.weathfold.demo.pause;

import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.game.SceneGame;

/**
 * 继续按钮
 * @author WeAthFolD
 */
public class ButtonResume extends T24Button {
	
	public ButtonResume(ScenePause scene) {
		super(scene, 211.0D, 328.0D, 400.0D, 54.0D, 
				ScenePause.TEX_RESUME0, ScenePause.TEX_RESUME1);
	}

	@Override
	public void onButtonReleased() {
		super.onButtonReleased();
		gameScene().isPaused = false;
	}

	@Override
	public void onFrameUpdate() {
		super.onFrameUpdate();
	}

	private SceneGame gameScene() {
		return ((ScenePause) this.sceneObj).gameScene;
	}
	
}