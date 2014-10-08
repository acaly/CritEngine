package cn.weathfold.demo.game.player.attributes;

import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.demo.game.obstacle.EntityObstacle;
import cn.weathfold.demo.game.player.EntityPlayer;

public class PlayerCollider extends AttrCollider
{
  public boolean thisTickCollided = false;
  private final EntityPlayer thePlayer;

  public PlayerCollider(EntityPlayer player)
  {
    this.thePlayer = player;
  }

  public boolean onCollided(RayTraceResult res)
  {
    boolean b;
    if ((b = res.collidedEntity instanceof EntityObstacle)) {
      this.thisTickCollided = true;
      EntityObstacle obstacle = (EntityObstacle)res.collidedEntity;
      if (!obstacle.collided) {
        obstacle.collided = true;

        if (obstacle.applyDamageAtSide(res.edge))
          this.thePlayer.attackEntity(obstacle, obstacle.dps);
      }
    }
    return b;
  }
}