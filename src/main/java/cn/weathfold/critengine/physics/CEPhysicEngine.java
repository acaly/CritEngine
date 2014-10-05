/**
 * 
 */
package cn.weathfold.critengine.physics;

import cn.weathfold.critengine.IEntityProcessor;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Vector2d;

/**
 * @author WeAthFolD
 *
 */
public class CEPhysicEngine implements IEntityProcessor {
	
	public static CEPhysicEngine INSTANCE = new CEPhysicEngine(); //单例大法好
	
	private CEPhysicEngine() {}
	
	protected static VelocityUpdater velUpdater = new VelocityUpdater();
	protected static CollisionHandler collider = new CollisionHandler();

	@Override
	public void processEntity(Entity e) {
		velUpdater.processEntity(e);
		collider.processEntity(e);
	}
	
	/* 执行一次光线追踪，并返回结果 */
	public static RayTraceResult rayTrace(Scene scene, Vector2d vec0, Vector2d vec1) {
		return new RayTraceResult();
	}
}
