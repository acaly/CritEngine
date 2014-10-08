/**
 * 
 */
package cn.weathfold.critengine.physics.attribute;

import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.physics.RayTraceResult;

/**
 * 碰撞器。添加以后该实体成为刚体，可以与世界中其他的刚体发生互动。
 * @author WeAthFolD
 */
public class AttrCollider implements Attribute {

	public float attnRate = 1.0F; //(反弹时)速度的衰减率 

	public boolean onGround = false; //实体是否在地面上

	public boolean doesMove = true; //实体碰撞时是否移动

	/**
	 * 设置碰撞速度衰减率
	 */
	public AttrCollider setAttnRate(float f) {
		attnRate = f;
		return this;
	}

	/**
	 * 设置自身被碰撞时是否移动
	 */
	public AttrCollider setMove(boolean b) {
		doesMove = b;
		return this;
	}

	/**
	 * 在碰撞发生时被调用的侦听函数
	 * @param res
	 * @return 返回false以阻止位置和速度修改
	 */
	public boolean onCollided(RayTraceResult res) {
		return doesMove;
	}

	@Override
	public String getAttributeID() {
		return "collider";
	}

}
