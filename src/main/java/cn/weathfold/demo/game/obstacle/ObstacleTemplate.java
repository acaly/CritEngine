package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.demo.game.SceneGame;

/**
 * 障碍实体的模板~参数略多哦
 * @author WeAthFolD
 */
public class ObstacleTemplate implements IEntityTemplate {
	
	public final double width;
	public final double height;
	public final double zHeight;
	public final int damage;
	public final String texture;
	public final boolean doesCollide;
	public final SceneGame scene;
	public double offsetX;
	public double offsetY;
	public double drawWidth;
	public double drawHeight;
	public boolean attackall;

	public ObstacleTemplate(SceneGame scene, double w, double h,
			double zHeight, int damage, String texture, boolean collide) {
		this.drawWidth = (this.width = w);
		this.drawHeight = (this.height = h);
		this.scene = scene;
		this.zHeight = zHeight;
		this.damage = damage;
		this.texture = texture;
		this.doesCollide = collide;
	}
	
	public ObstacleTemplate setAttackAllSide() {
		attackall = true;
		return this;
	}

	public ObstacleTemplate setRenderProps(float x, float y, float dw, float dh) {
		this.offsetX = x;
		this.offsetY = y;
		this.drawWidth = dw;
		this.drawHeight = dh;
		return this;
	}

	@Override
	public void generate(double x, double y) {
		this.scene.spawnEntity(new EntityObstacle(this.scene, x, y, this.width,
				this.height, this.texture, this.zHeight, this.damage,
				this.doesCollide, this.offsetX, this.offsetY, this.drawWidth,
				this.drawHeight, attackall));
	}
}