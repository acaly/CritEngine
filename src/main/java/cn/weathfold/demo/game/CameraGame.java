package cn.weathfold.demo.game;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 游戏摄像机~
 * @author WeAthFolD
 */
public class CameraGame extends Camera {
	
	public CameraGame(Scene scene) {
		super(scene, -512.0D, 0.0D, 819.0D, 512.0D,
				Camera.Alignment.ALIGN_WIDTH);
	}

	@Override
	public void onFrameUpdate() {
		if (getScene().gameOver) {
			return;
		}
		Rect rect = getGeomProps();
		rect.setX(rect.getMinX() - EntityPlayer.SPEED_NORMAL
				* CritEngine.getTimer().getElapsedTime() / 1000.0D);
	}

	public void resetPosition() {
		getGeomProps().setX(-512.0D);
	}

	private SceneGame getScene() {
		return (SceneGame) this.sceneObj;
	}
}