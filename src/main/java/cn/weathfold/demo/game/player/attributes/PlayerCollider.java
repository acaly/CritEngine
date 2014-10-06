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

	/**
	 * 
	 */
	public PlayerCollider() {
		// TODO Auto-generated constructor stub
	}

	public boolean onCollided(RayTraceResult res) {
		return false;
	}
}
