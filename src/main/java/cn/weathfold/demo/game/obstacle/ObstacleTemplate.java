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
	public final boolean doesCollide;
	public final SceneGame scene;
	
	public ObstacleTemplate(SceneGame scene, double w, double h, double zHeight, double dps, String texture, boolean collide) {
		width = w;
		height = h;
		this.scene = scene;
		this.zHeight = zHeight;
		this.dps = dps;
		this.texture = texture;
		doesCollide = collide;
	}
	
	public void generate(double x, double y) {
		scene.spawnEntity(new EntityObstacle(scene, x, y, width, height, texture, zHeight, dps, doesCollide));
	}

	//public void generateRange(Scen)
}
