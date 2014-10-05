/**
 * 
 */
package cn.weathfold.client;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class EntityBouncingBall extends Entity {

	public EntityBouncingBall(Scene scene, double x, double y, double size) {
		super(scene, x, y, size, size);
		this.addAttribute(new AttrVelocity().setGravity(0.1D));
		this.addAttribute(new AttrCollider());
	}
	
	public Entity setVelocity(double x, double y) {
		AttrVelocity vel = (AttrVelocity) this.getAttribute("velocity");
		vel.vel.x = x;
		vel.vel.y = y;
		return this;
	}
	
	public Entity setTexture(String tex) {
		this.setTexture(tex, 0, 0, 1, 1);
		return this;
	}

}
