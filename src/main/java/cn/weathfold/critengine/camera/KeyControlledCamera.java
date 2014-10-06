/**
 * 
 */
package cn.weathfold.critengine.camera;

import cn.weathfold.critengine.input.Directioner;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

/**
 * @author WeAthFolD
 *
 */
public class KeyControlledCamera extends Camera {
	
	Directioner directioner = new Directioner();
	float velFactorX = 1.0F, velFactorY;

	/**
	 * @param scene
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param align
	 */
	public KeyControlledCamera(Scene scene, double x, double y, double width,
			double height, Alignment align) {
		super(scene, x, y, width, height, align);
	}
	
	/**
	 * 设置x、y轴的移动速度(格/秒)
	 * @param f1
	 * @param f2
	 * @return
	 */
	public KeyControlledCamera setVelFactor(float f1, float f2) {
		velFactorX = f1;
		velFactorY = f2;
		return this;
	}
	
	@Override
	public void onFrameUpdate() {
		directioner.frameUpdate();
		Rect rt = this.getGeomProps();
		rt.pos.x += velFactorX * directioner.dirHorizonal;
		rt.pos.y += velFactorY * directioner.dirVertical;
	}

}
