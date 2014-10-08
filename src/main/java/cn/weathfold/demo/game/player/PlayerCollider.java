package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.demo.game.obstacle.EntityObstacle;

/**
 * 玩家的碰撞控制器
 * @author WeAthFolD
 */
public class PlayerCollider extends AttrCollider {
	
	public boolean thisTickCollided = false;
	private final EntityPlayer thePlayer;

	public PlayerCollider(EntityPlayer player) {
		this.thePlayer = player;
	}

	@Override
	public boolean onCollided(RayTraceResult res) {
		boolean b;
		
		//只筛选Obstacle，同时做和跳跃支持相关的辅助
		if ((b = res.collidedEntity instanceof EntityObstacle)) {
			this.thisTickCollided = true;
			EntityObstacle obstacle = (EntityObstacle) res.collidedEntity;
			if (!obstacle.collided) {
				obstacle.collided = true;
				if (obstacle.applyDamageAtSide(res.edge))
					this.thePlayer.attackEntity(obstacle, obstacle.hitDamage);
			}
			
			AttrGeometry attr = thePlayer.getGeomProps();
			if(attr instanceof GeomWrapped) {
				if(!((GeomWrapped)attr).onCollided(res))
					return false;
			}
		}
		return b;
	}
}