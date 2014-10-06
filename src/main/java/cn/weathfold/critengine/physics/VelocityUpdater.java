/**
 * 
 */
package cn.weathfold.critengine.physics;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.IEntityProcessor;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.util.Vector2d;

/**
 * 处理速度更新
 * @author WeAthFolD
 *
 */
public class VelocityUpdater implements IEntityProcessor {


	@Override
	public void processEntity(Entity e) {
		AttrGeometry pos = e.getGeomProps();
		AttrVelocity vel = (AttrVelocity) e.getAttribute("velocity");
		if(vel == null || !vel.preVelUpdate()) return;
		
		//System.out.println("")
		long passedTime = CritEngine.getTimer().getElapsedTime();
		Vector2d newPos = pos.pos.copy();
		
		newPos.addVector(vel.vel.x * passedTime / 1000D, vel.vel.y * passedTime / 1000D);
		vel.vel.addVector(vel.accelDir.x * vel.gravity * passedTime / 80D, vel.accelDir.y * vel.gravity * passedTime / 80D);
		
		AttrCollider ac = (AttrCollider) e.getAttribute("collider");
		if(ac != null) {
			CEPhysicEngine.collider.handlesVelUpdate(e, pos, newPos);
		} else {
			pos.pos = newPos;
		}
	}

}
