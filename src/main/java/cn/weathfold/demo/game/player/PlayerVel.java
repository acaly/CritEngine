/**
 * 
 */
package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.demo.game.SceneGame;

/**
 * 玩家的速度属性
 * @author WeAthFolD
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

	@Override
	public boolean preVelUpdate() {
		return !sceneObj.gameOver;
	}

	@Override
	public boolean onVelocityChange(Entity target) {
		return false;
	}

	public void frameUpdate() {
		Camera camera = sceneObj.mainCamera;
		AttrGeometry geom = thePlayer.getGeomProps(), camGeom = camera
				.getGeomProps();

		if (geom.getMinX() < camGeom.getMinX()
				+ EntityPlayer.DEFAULT_SCREEN_OFFSET) {
			geom.setX(camGeom.getMinX() + EntityPlayer.DEFAULT_SCREEN_OFFSET);
		}
	}

}
