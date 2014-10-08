/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.gui.GUIComponent;

/**
 * GUI摄像机(GUI部分存储在(0, 0) -> (819, 512))
 * @author WeAthFolD
 *
 */
public class CameraGameGUI extends Camera {

	public CameraGameGUI(SceneGame scene) {
		super(scene, 0, 0, 819, 512, Alignment.ALIGN_WIDTH);
	}

	@Override
	protected boolean doesDrawEntity(Entity e) {
		return e instanceof GUIComponent;
	}

}
