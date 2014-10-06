/**
 * 
 */
package cn.weathfold.demo.game.player.attributes;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * @author WeAthFolD
 *
 */
public class PlayerVel extends AttrVelocity {

	SceneGame sceneObj;
	EntityPlayer thePlayer;
	
	/**
	 * 
	 */
	public PlayerVel(EntityPlayer player) {
		sceneObj = (SceneGame) player.sceneObj;
		thePlayer = player;
		this.vel.x = -EntityPlayer.SPEED_CATCHUP;
		this.gravity = 0;
	}
	
	public void frameUpdate() {
		Camera camera = sceneObj.mainCamera;
		AttrGeometry geom = thePlayer.getGeomProps(),
				camGeom = camera.getGeomProps();
		
		if(geom.getMinX() < camGeom.getMinX() + EntityPlayer.DEFAULT_SCREEN_OFFSET) {
			geom.pos.x = camGeom.pos.x + EntityPlayer.DEFAULT_SCREEN_OFFSET;
		}
	}

}
