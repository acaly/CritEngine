/**
 * 
 */
package cn.weathfold.critengine.physics;

import java.util.Iterator;
import java.util.Set;

import cn.weathfold.critengine.IEntityProcessor;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.RayTraceResult.EnumEdgeSide;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.util.Vector2d;

/**
 * 由VelocityUpdater或物理引擎本体进行调用，执行碰撞检查的工作。
 * @author WeAthFolD
 */
public class CollisionHandler implements IEntityProcessor {
	
	private static class CollidableEntityFilter implements IEntityFilter {

		@Override
		public boolean isEntityApplicable(Entity ent) {
			return ent.hasAttribute("collider");
		}
		
	}
	
	private static IEntityFilter filter = new CollidableEntityFilter();

	@Override
	public void processEntity(Entity e) {
		if(!filter.isEntityApplicable(e)) {
			return;
		}
		AttrCollider collider = (AttrCollider) e.getAttribute("collider");
		if(!collider.doesMove)
			return;
		
		Set<Entity> set = e.sceneObj.getEntitiesWithin(e.getGeomProps(), filter, e);
		if(set.isEmpty()) {
			return;
		}
		
		//System.out.println(set);
		//System.out.println(e.getGeomProps().getMinX() + ", " + e.getGeomProps().getMinY());
		Iterator<Entity> iter = set.iterator();
		Rect rect = new Rect(iter.next().getGeomProps());
		//比较鬼畜的算法，当然一般只会碰撞到一个实体所以问题不会太大……吧……
		while(iter.hasNext()) {
			Rect r2 = iter.next().getGeomProps();
			rect.expand(r2);
		}
		EnumEdgeSide side = alignEntity(e, rect);
		Entity ce = set.iterator().next();
		if(!collider.onCollided(new RayTraceResult(side, ce, ce.getGeomProps().pos)))
			return;
		//反弹
		AttrVelocity attrVel = (AttrVelocity) e.getAttribute("velocity");
		if(attrVel == null)
			return;
		if(side.dirX != 0)
			attrVel.vel.x = Math.abs(attrVel.vel.x) * side.dirX * collider.attnRate;
		if(side.dirY != 0)
			attrVel.vel.y = Math.abs(attrVel.vel.y) * side.dirY * collider.attnRate;
	}
	
	private int sgn(double x) {
		return x > 0 ? 1 : x == 0 ? 0 : -1;
	}
	
	public void handlesVelUpdate(Entity e, AttrGeometry pre, Vector2d after) {
		AttrVelocity vel = (AttrVelocity) e.getAttribute("velocity");
		AttrCollider collider = (AttrCollider) e.getAttribute("collider");
		if(!collider.doesMove)
			return;
		
		double offsetX = pre.width,
			   offsetY = pre.height;	
		//pre.pos.addVector(offsetX, offsetY);
		//after.addVector(offsetX, offsetY);
		
		RayTraceResult res = CEPhysicEngine.rayTrace(e.sceneObj, pre.pos, after, filter, e);
		
		//pre.pos.addVector(-offsetX, -offsetY);
		//after.addVector(-offsetX, -offsetY);
		
		//System.out.println(res.collided);
		if(!res.collided) {
			pre.pos = after;
			return;
		}
		//System.out.println(res.edge);
		if(!collider.onCollided(res))
			return;
		//愉快的反弹
		if(res.edge.dirX != 0)
			vel.vel.x = Math.abs(vel.vel.x) * res.edge.dirX * collider.attnRate;
		if(res.edge.dirY != 0)
			vel.vel.y = Math.abs(vel.vel.y) * res.edge.dirY * collider.attnRate;
		
		//System.out.println(e + " CH " + res.edge + " " + res.collidedEntity);
		alignEntity(e, res.collidedEntity.getGeomProps(), res.edge);
	}
	
	/**
	 * 将实体就近对齐到某个区域旁边
	 **/
	private EnumEdgeSide alignEntity(Entity targ, Rect bound) {
		AttrGeometry 
			geo0 = targ.getGeomProps();
		
		double min = Double.MAX_VALUE;
		
		Vector2d res = null;
		EnumEdgeSide res2 = EnumEdgeSide.NONE;
		//EnumEdgeSide rSide = null;
		for(EnumEdgeSide side : EnumEdgeSide.values()) {
			if(side == EnumEdgeSide.NONE)
				continue;
			Vector2d vec = virtualAlign(targ, bound, side);
			double dist = Math.abs(vec.x - geo0.pos.x) + Math.abs(vec.y - geo0.pos.y);
			if(dist < min) {
				res = vec;
				min = dist;
				res2 = side;
				//rSide = side;
			}
		}
		//res.addVector(rSide.dirX * 0.001, rSide.dirY * 0.001);
		geo0.pos = res;
		return res2;
	}
	
	private void alignEntity(Entity targ, Rect bound, EnumEdgeSide side) {
		AttrGeometry geom = targ.getGeomProps();
		geom.pos = virtualAlign(targ, bound, side);
	}
	
	private Vector2d virtualAlign(Entity targ, Rect bound, EnumEdgeSide side) {
		AttrGeometry geom = targ.getGeomProps();
		Vector2d res = geom.pos.copy();
		switch(side) {
		case BOTTOM:
			res.y = bound.getMinY() - geom.height;
			break;
		case LEFT:
			res.x = bound.getMinX() - geom.width;
			break;
		case RIGHT:
			res.x = bound.getMaxX();
			break;
		case TOP:
			res.y = bound.getMaxY();
			break;
		default:
			break;
		}
		return res;
	}

}
