/**
 * 
 */
package cn.weathfold.critengine.physics.attribute;

import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.physics.RayTraceResult;

/**
 * @author WeAthFolD
 *
 */
public class AttrCollider implements Attribute {
	
	/* (反弹后)速度的衰减率 */
	public float attnRate = 1.0F;

	/* 实体是否在地面上 */
	public boolean onGround = false;
	
	/* 实体碰撞时是否移动 */
	public boolean doesMove = true;
	
	public AttrCollider setAttnRate(float f) {
		attnRate = f;
		return this;
	}
	
	public AttrCollider setMove(boolean b) {
		doesMove = b;
		return this;
	}
	
	/**
	 * 在碰撞发生时被调用的侦听函数
	 * @param res
	 * @return 返回false以阻止速度修改
	 */
	public boolean onCollided(RayTraceResult res) {
		return true;
	}
	
	@Override
	public String getAttributeID() {
		return "collider";
	}

}
