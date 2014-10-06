/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * @author WeAthFolD
 *
 */
public class CameraGame extends Camera {

	/**
	 * @param scene
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param align
	 */
	public CameraGame(Scene scene) {
		super(scene, -512, 0, 819, 512, Alignment.ALIGN_WIDTH);
	}
	
	@Override
	public void onFrameUpdate() {
		this.getGeomProps().pos.x -= EntityPlayer.SPEED_NORMAL * CritEngine.getTimer().getElapsedTime() / 1000D;
	}
	
	private SceneGame getScene() {
		return (SceneGame) this.sceneObj;
	}

}
