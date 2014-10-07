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
	
	/* 速度大小 */
	public Vector2d vel = new Vector2d();
	
	/* 重力加速度方向，目前对任意方向重力的支持还不完善 */
	public Vector2d accelDir = new Vector2d(0, -1);
	
	/* 重力加速度大小 */
	public double gravity = 8;
	
	
	public AttrVelocity() {
	}
	
	public AttrVelocity setGravity(double d) {
		gravity = d;
		return this;
	}
	
	public AttrVelocity setGravityDir(double x, double y) {
		accelDir.x = x;
		accelDir.y = y;
		accelDir.normalize();
		return this;
	}
	
	public boolean preVelUpdate() {
		return true;
	}
	
	public boolean onVelocityChange(Entity target) {
		return true;
	}
	
	@Override
	public String getAttributeID() {
		return "velocity";
	}

}
