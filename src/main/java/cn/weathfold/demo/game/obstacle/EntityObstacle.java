/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.scene.Scene;

/**
 * 
 * @author WeAthFolD
 *
 */
public class EntityObstacle extends Entity {
	
	public final double zHeight; //虚拟高度
	public final double dps; //每秒伤害输出

	public EntityObstacle(Scene scene, double x, double y, double width,
			double height, String texture, double zHeight, double dps) {
		super(scene, x, y, width, height);
		this.zHeight = zHeight;
		this.dps = dps;
		this.setTexture(texture);
		this.addAttribute(new AttrCollider() {
			{
				doesMove = false;
			}
		});
	}

}
