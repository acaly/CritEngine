/**
 * 
 */
package cn.weathfold.critengine.camera;

import cn.weathfold.critengine.input.Directioner;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

/**
 * 键盘可以控制的摄像机（默认上下左右）
 * @author WeAthFolD
 */
public class KeyControlledCamera extends Camera {

	Directioner directioner = new Directioner();
	float velX = 1.0F, velY = 1.0F;

	public KeyControlledCamera(Scene scene, double x, double y, double width,
			double height, Alignment align) {
		super(scene, x, y, width, height, align);
	}

	/**
	 * 设置x、y轴的移动速度(格/秒)
	 */
	public KeyControlledCamera setVelocity(float vx, float vy) {
		velX = vx;
		velY = vy;
		return this;
	}

	@Override
	public void onFrameUpdate() {
		directioner.frameUpdate();
		Rect rt = this.getGeomProps();
		rt.move(velX * directioner.dirHorizonal, velY * directioner.dirVertical);
	}

}
