/**
 * 
 */
package cn.weathfold.critengine.physics.attribute;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.util.Vector2d;

/**
 * 速度属性。添加后物理引擎会自动接管物体的位置更新。
 * @author WeAthFolD
 */
public class AttrVelocity implements Attribute {

	public Vector2d vel = new Vector2d(); //速度大小

	public Vector2d accelDir = new Vector2d(0, -1); //重力加速度方向，目前对任意方向重力的支持还不完善

	public double gravity = 8; //重力加速度大小 

	public AttrVelocity() {}

	/**
	 * 设置重力加速度的大小
	 * @return
	 */
	public AttrVelocity setGravity(double d) {
		gravity = d;
		return this;
	}

	/**
	 * 设置重力加速度的方向
	 */
	public AttrVelocity setGravityDir(double x, double y) {
		accelDir.x = x;
		accelDir.y = y;
		accelDir.normalize();
		return this;
	}

	/**
	 * 在速度更新前被调用
	 * @return 返回false以阻止进一步更新
	 */
	public boolean preVelUpdate() {
		return true;
	}

	/**
	 * 在碰撞时被调用
	 * @param target
	 * @return 返回false以阻止进一步操作
	 */
	public boolean onVelocityChange(Entity target) {
		return true;
	}

	@Override
	public String getAttributeID() {
		return "velocity";
	}

}
