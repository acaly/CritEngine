/**
 * 
 */
package cn.weathfold.critengine.physics;

import cn.weathfold.critengine.entity.Entity;

/**
 * “光线追踪”结果，包含了哪条边被碰撞到，以及被碰撞到的实体。
 * @author WeAthFolD
 *
 */
public class RayTraceResult {
	
	public enum EnumEdgeSide {
		NONE(0, 0), LEFT(-1, 0), RIGHT(1, 0), TOP(0, 1), BOTTOM(0, -1);
		EnumEdgeSide(int x, int y) {
			dirX = x;
			dirY = y;
		}
		public int dirX, dirY;
	}
	
	public boolean collided; //是否遇到了实体？
	public EnumEdgeSide edge; //碰撞到的边
	public Entity collidedEntity;
	
	public RayTraceResult() {
		collided = false;
		edge = EnumEdgeSide.NONE;
	}
	
	public RayTraceResult(EnumEdgeSide side, Entity e) {
		collided = true;
		edge = side;
		collidedEntity = e;
	}
}
