package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.demo.game.obstacle.EntityObstacle;

public class ObstacleFilter
  implements IEntityFilter
{
  public static IEntityFilter INSTANCE = new ObstacleFilter();

  public boolean isEntityApplicable(Entity ent)
  {
    return ent instanceof EntityObstacle;
  }
}