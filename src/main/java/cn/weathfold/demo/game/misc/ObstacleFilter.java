package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.demo.game.obstacle.EntityObstacle;

/**
 * 只通过EntityObstacle的滤镜
 * @author WeAthFolD
 */
public class ObstacleFilter implements IEntityFilter {
	
	public static IEntityFilter INSTANCE = new ObstacleFilter();

	@Override
	public boolean isEntityApplicable(Entity ent) {
		return ent instanceof EntityObstacle;
	}
	
}