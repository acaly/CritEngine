/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.gui.GUIComponent;

/**
 * @author WeAthFolD
 *
 */
public class CameraGameGUI extends Camera {

	public CameraGameGUI(SceneGame scene) {
		super(scene, 0, 0, 819, 512, Alignment.ALIGN_WIDTH);
	}
	
	protected boolean drawEntity(Entity e) {
		return e instanceof GUIComponent;
	}

}
