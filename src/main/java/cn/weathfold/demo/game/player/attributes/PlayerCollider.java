/**
 * 
 */
package cn.weathfold.demo.game.player.attributes;

import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.demo.game.obstacle.EntityObstacle;

/**
 * @author WeAthFolD
 *
 */
public class PlayerCollider extends AttrCollider {
	
	public boolean thisTickCollided = false;

	/**
	 * 
	 */
	public PlayerCollider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCollided(RayTraceResult res) {
		if(res.collided) {
			thisTickCollided = true;
		}
		return res.collidedEntity instanceof EntityObstacle;
	}
}
