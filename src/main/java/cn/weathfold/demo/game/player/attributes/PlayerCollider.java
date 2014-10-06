/**
 * 
 */
package cn.weathfold.demo.game.player.attributes;

import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;

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

	public boolean onCollided(RayTraceResult res) {
		if(res.collided) {
			thisTickCollided = true;
		}
		return false;
	}
}
