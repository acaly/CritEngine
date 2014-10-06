/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import cn.weathfold.demo.game.SceneGame;

/**
 * @author WeAthFolD
 *
 */
public class ObstacleTemplate {

	public final double width, height, zHeight, dps;
	public final String texture;
	
	public ObstacleTemplate(double w, double h, double zHeight, double dps, String texture) {
		width = w;
		height = h;
		this.zHeight = zHeight;
		this.dps = dps;
		this.texture = texture;
	}
	
	public void generate(SceneGame scene, double x, double y) {
		scene.spawnEntity(new EntityObstacle(scene, x, y, width, height, texture, zHeight, dps));
	}

}
