/**
 * 
 */
package cn.weathfold.demo.game;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class SceneGame extends Scene {

	/**
	 * 
	 */
	public SceneGame() {
		this.mainCamera = new Camera(this, 0, 0, 512, 512, Alignment.ALIGN_WIDTH);
	}

}
