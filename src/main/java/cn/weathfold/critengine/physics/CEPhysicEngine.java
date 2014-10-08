/**
 * 
 */
package cn.weathfold.critengine.physics;

import java.util.Set;

import cn.weathfold.critengine.IEntityProcessor;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.critengine.physics.RayTraceResult.EnumEdgeSide;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.util.Vector2d;

/**
 * 物理引擎（教练：这么渣的也敢叫物理引擎？！）
 * @author WeAthFolD
 */
public class CEPhysicEngine implements IEntityProcessor {

	public static CEPhysicEngine INSTANCE = new CEPhysicEngine(); // 单例大法好
	private static final RayTraceResult NULL_RESULT = new RayTraceResult();

	private CEPhysicEngine() {
	}

	protected static VelocityUpdater velUpdater = new VelocityUpdater();
	protected static CollisionHandler collider = new CollisionHandler();

	@Override
	public void processEntity(Entity e) {
		velUpdater.processEntity(e);
		collider.processEntity(e);
	}

	public static RayTraceResult rayTrace(Scene scene, Vector2d vec0,
			Vector2d vec1) {
		return rayTrace(scene, vec0, vec1, null);
	}

	/**
	 * 执行一次光线追踪并返回结果
	 * @param scene 场景
	 * @param vec0 起始点
	 * @param vec1 结束点
	 * @param filter 实体滤镜
	 * @param exclusions 不需要计入的实体们
	 * @return 碰撞结果
	 */
	public static RayTraceResult rayTrace(Scene scene, Vector2d vec0,
			Vector2d vec1, IEntityFilter filter, Entity... exclusions) {
		double x0 = vec0.x, y0 = vec0.y, x1 = vec1.x, y1 = vec1.y;
		double tmp;
		if (x0 > x1) {
			tmp = x0;
			x0 = x1;
			x1 = tmp;
		}
		if (y0 > y1) {
			tmp = y0;
			y0 = y1;
			y1 = tmp;
		}
		Rect rect = new Rect(x0, y0, x1, y1);
		Set<Entity> set = scene.getEntitiesWithin(rect, filter, exclusions);

		double minDist = Double.MAX_VALUE;
		RayTraceResult res = NULL_RESULT;
		for (Entity e : set) {
			RayTraceResult rt = solve(vec0, vec1, e);
			if (rt.collided) {
				double dist = rt.hitPos.distanceTo(vec0);
				if (dist < minDist) {
					minDist = dist;
					res = rt;
				}
			}
		}

		return res;
	}

	/**
	 * 谜の直线方程求解
	 */
	private static RayTraceResult solve(Vector2d beg, Vector2d end, Entity ent) {
		double dx = end.x - beg.x, dy = end.y - beg.y;

		Rect rect = ent.getGeomProps();

		if (beg.equals(end))
			return NULL_RESULT;

		if (dx == 0) { // Vertical line
			if (dy > 0) {
				return inrange(beg.y, rect.getMinY(), end.y) ? new RayTraceResult(
						EnumEdgeSide.BOTTOM, ent, new Vector2d(beg.x,
								rect.getMinY())) : NULL_RESULT;
			} else {
				return inrange(end.y, rect.getMaxY(), beg.y) ? new RayTraceResult(
						EnumEdgeSide.TOP, ent, new Vector2d(beg.x,
								rect.getMaxY())) : NULL_RESULT;
			}
		}
		if (dy == 0) { // Horizonal line
			// System.out.print("J[");
			if (dx > 0) {
				// System.out.println("JDG");
				return inrange(beg.x, rect.getMinX(), end.x) ? new RayTraceResult(
						EnumEdgeSide.LEFT, ent, new Vector2d(
								rect.getMinX() - 0.001, beg.y)) : NULL_RESULT;
			} else {
				// System.out.println("JDG2");
				return inrange(end.x, rect.getMaxX(), beg.x) ? new RayTraceResult(
						EnumEdgeSide.RIGHT, ent, new Vector2d(rect.getMaxX(),
								beg.y)) : NULL_RESULT;
			}
		}

		double k = dy / dx, b = (end.x * beg.y - beg.x * end.y) / dx;
		double temp = 0;
		double a0, a1; // Begin and End（x或y）
		if (dx > 0) {
			a0 = beg.x;
			a1 = end.x;
		} else {
			a0 = end.x;
			a1 = beg.x;
		}

		// 首先判断上下
		if (dy > 0) { // BOTTOM?
			temp = (rect.getMinY() - b) / k;
			if (inrange(a0, temp, a1))
				return new RayTraceResult(EnumEdgeSide.BOTTOM, ent,
						new Vector2d(temp, rect.getMinY()));
		} else { // TOP?
			temp = (rect.getMaxY() - b) / k;
			if (inrange(a0, temp, a1))
				return new RayTraceResult(EnumEdgeSide.TOP, ent, new Vector2d(
						temp, rect.getMaxY()));
		}

		if (dy > 0) {
			a0 = beg.y;
			a1 = end.y;
		} else {
			a0 = end.y;
			a1 = beg.y;
		}

		// 然后判断左右
		if (dx > 0) { // LEFT?
			temp = k * rect.getMinX() + b;
			if (inrange(a0, temp, a1))
				return new RayTraceResult(EnumEdgeSide.LEFT, ent, new Vector2d(
						rect.getMinX(), temp));
		} else { // RIGHT?
			temp = k * rect.getMaxX() + b;
			if (inrange(a0, temp, a1))
				return new RayTraceResult(EnumEdgeSide.RIGHT, ent,
						new Vector2d(rect.getMaxX(), temp));
		}

		return NULL_RESULT;
	}

	private static boolean inrange(double a, double b, double c) {
		return a <= b && b <= c;
	}

}
