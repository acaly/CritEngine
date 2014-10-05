/**
 * 
 */
package cn.weathfold.critengine.entity;

import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.scene.Scene;

/**
 * 无法移动的固定实体（一般是墙什么的）
 * @author WeAthFolD
 */
public class EntitySolid extends Entity {

	public EntitySolid(Scene scene, double x, double y, double width, double height) {
		super(scene, x, y, width, height);
		AttrCollider collider = new AttrCollider().setAttnRate(0F).setMove(false);
		this.addAttribute(collider);
	}

}
