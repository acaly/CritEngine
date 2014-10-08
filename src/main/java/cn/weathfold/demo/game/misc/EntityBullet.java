package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

/**
 * 子弹实体
 * @author WeAthFolD
 */
public class EntityBullet extends Entity {
	
	static final double 
		WIDTH = 32.0D,
		HEIGHT = 16.0D,
		SPEED = 1200.0D;
	
	Entity thrower;
	LoopAnimation anim;
	
	//Props
	public boolean isPlayer;
	int damage;
	int yOffset;
	
	Attribute attrCollider = new AttrCollider() {
		@Override
		public boolean onCollided(RayTraceResult res) {
			if (((EntityBullet.this.isPlayer) && (!(res.collidedEntity instanceof EntityPlayer))) || 
				((!EntityBullet.this.isPlayer) && ((res.collidedEntity instanceof EntityPlayer)))) {
				res.collidedEntity.attackEntity(EntityBullet.this.thrower, EntityBullet.this.damage);
				EntityBullet.this.deathFlag = true;
			}
			return false;
		}
	};
	Attribute attrVelocity;

	public EntityBullet(Scene scene, Entity shooter) {
		super(scene);
		this.thrower = shooter;

		addAttribute(this.attrCollider);

		AttrGeometry geom = getGeomProps();
		AttrGeometry shooterGeom = shooter.getGeomProps();

		if ((this.thrower instanceof EntityPlayer))
			this.isPlayer = true;
		else
			this.yOffset = 60;

		this.damage = (this.isPlayer ? 20 : 10);

		geom.setX(this.isPlayer ? shooterGeom.getMinX() : shooterGeom.getMaxX());
		geom.setWidth(WIDTH);
		geom.setHeight(HEIGHT);
		geom.setY(this.isPlayer ? shooterGeom.getMaxY() + 25.0D
				: shooterGeom.getMinY() + 34.0D);

		this.attrVelocity = new AttrVelocity() {
			{
				this.vel.x = isPlayer ? -SPEED : SPEED;
				this.gravity = 0;
			}
		};
		this.anim = new LoopAnimation(SceneGame.TEX_BULLET)
				.setDrawingQuad(new Rect(0.0D, 0.0D, 32.0D, 16.0D));
		addAttribute(this.attrVelocity);
	}

	@Override
	public void drawEntity() {
		GL11.glPushMatrix(); {

			AttrGeometry geom = getGeomProps();
			CERenderEngine.bindTexture(SceneGame.TEX_EDGE);
			RenderUtils.renderTexturedQuads(getGeomProps());

			GL11.glTranslated(geom.getMinX(), geom.getMinY(), 0.0D);
			if (!this.isPlayer) {
				GL11.glTranslated(geom.getWidth(), geom.getHeight() + this.yOffset, 0.0D);
				GL11.glScalef(-1.0F, 1.0F, 1.0F);
			}
			this.anim.draw();

		} GL11.glPopMatrix();
	}

	@Override
	public void onFrameUpdate() {
		if (!this.sceneObj.mainCamera.getGeomProps().intersects(getGeomProps()))
			this.deathFlag = true;
	}
}