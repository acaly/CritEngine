/**
 * 
 */
package cn.weathfold.demo.game.misc;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.*;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * @author WeAthFolD
 *
 */
public class EntityBullet extends Entity {
	
	int DEFAULT_DAMAGE = 20;
	static final double
		WIDTH = 32,
		HEIGHT = 16,
		SPEED = 1200;
	
	Entity thrower;
	boolean isPlayer;
	
	Attribute
		attrCollider = new AttrCollider() {
			public boolean onCollided(RayTraceResult res) {
				if(isPlayer && !(res.collidedEntity instanceof EntityPlayer)) {
					System.out.println("Attacked nonplayer " + res.collidedEntity);
					res.collidedEntity.attackEntity(EntityBullet.this, DEFAULT_DAMAGE);
					deathFlag = true;
				}
				if(!isPlayer && res.collidedEntity instanceof EntityPlayer) {
					res.collidedEntity.attackEntity(EntityBullet.this, DEFAULT_DAMAGE);
					deathFlag = true;
				}
				
				return false;
			}
		},
		attrVelocity;
	
	/**
	 * @param scene
	 */
	public EntityBullet(Scene scene, Entity shooter) {
		super(scene);
		thrower = shooter;
		
		addAttribute(attrCollider);
		AttrGeometry geom = this.getGeomProps(),
				shooterGeom = shooter.getGeomProps();
		geom.width = WIDTH;
		geom.height = HEIGHT;
		geom.pos.y = shooterGeom.getMaxY() + 32;
		if(thrower instanceof EntityPlayer) {
			isPlayer = true; 
			geom.pos.x = shooterGeom.getMinX() - WIDTH;
		} else {
			geom.pos.x = shooterGeom.getMaxX();
		}
		
		attrVelocity  = new AttrVelocity() { 
			{
				this.gravity = 0;
				if(isPlayer) {
					this.vel.x = -SPEED;
				} else {
					this.vel.x = SPEED;
				}
			}
		};
		addAttribute(attrVelocity);
		System.out.println("A");
	}
	
	@Override
	public void drawEntity() {
		GL11.glPushMatrix();
		
		AttrGeometry geom = (AttrGeometry) this.getAttribute("geometry");
		GL11.glTranslated(geom.getMinX(), geom.getMinY(), 0);
		((SceneGame)sceneObj).animBullet.draw();
		
		GL11.glPopMatrix();
	}

}
