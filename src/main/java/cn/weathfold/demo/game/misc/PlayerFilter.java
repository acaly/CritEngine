package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.demo.game.player.EntityPlayer;

public class PlayerFilter
  implements IEntityFilter
{
  public static IEntityFilter INSTANCE = new PlayerFilter();

  public boolean isEntityApplicable(Entity ent)
  {
    return ent instanceof EntityPlayer;
  }
}