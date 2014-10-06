/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.demo.game.SceneGame;

/**
 * @author WeAthFolD
 *
 */
public class ObstacleTemplate implements IEntityTemplate {

	public final double width, height, zHeight, dps;
	public final String texture;
	public final boolean doesCollide;
	public final SceneGame scene;
	public double offsetX, offsetY;
	public double drawWidth, drawHeight;
	
	public ObstacleTemplate(SceneGame scene, double w, double h, double zHeight, double dps, String texture, boolean collide) {
		drawWidth = width = w;
		drawHeight = height = h;
		this.scene = scene;
		this.zHeight = zHeight;
		this.dps = dps;
		this.texture = texture;
		doesCollide = collide;
	}
	
	public ObstacleTemplate setRenderProps(float x, float y, float dw, float dh) {
		offsetX = x;
		offsetY = y;
		drawWidth = dw;
		drawHeight = dh;
		return this;
	}
	
	public void generate(double x, double y) {
		scene.spawnEntity(new EntityObstacle(scene, x, y, width, height, texture,
				zHeight, dps, doesCollide, offsetX, offsetY, drawWidth, drawHeight));
	}
	
	

	//public void generateRange(Scen)
}
