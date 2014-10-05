/**
 * 
 */
package cn.weathfold.critengine.physics.attribute;

import cn.weathfold.critengine.entity.attribute.Attribute;

/**
 * @author WeAthFolD
 *
 */
public class AttrCollider implements Attribute {
	
	/* (反弹后)速度的衰减率 */
	public float attnRate = 0.9F;

	/* 实体是否在地面上 */
	public boolean onGround = false;
	
	public AttrCollider setAttnRate(float f) {
		attnRate = f;
		return this;
	}
	
	@Override
	public String getAttributeID() {
		return "collider";
	}

}
