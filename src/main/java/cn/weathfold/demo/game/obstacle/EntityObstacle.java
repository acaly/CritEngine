package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import org.lwjgl.opengl.GL11;

/**
 * 所有游戏内障碍物的通用实体
 * @author WeAthFolD
 */
public class EntityObstacle extends Entity {
	
	public int hitDamage;
	public double zHeight, offsetX, offsetY;
	public Rect drawRect;
	public boolean collided, attackall;

	/**
	 * 这个是给模板生成用的构造器，不是抖M千万别用
	 */
	public EntityObstacle(Scene scene, double x, double y, double width,
			double height, String texture, double zHeight, int hitDamage,
			boolean hit, double offX, double offY, double drawWidth,
			double drawHeight, boolean aa) {
		super(scene, x, y, width, height);
		this.zHeight = zHeight;
		this.hitDamage = hitDamage;
		this.offsetX = offX;
		this.offsetY = offY;
		this.drawRect = new Rect(0.0D, 0.0D, drawWidth, drawHeight);
		setTexture(texture);
		if (hit)
			addAttribute(new AttrCollider() {
				@Override
				public boolean onCollided(RayTraceResult res) {
					return false;
				}
			});
		attackall = aa;
	}

	public EntityObstacle(Scene scene, double x, double y, double width,
			double height) {
		super(scene, x, y, width, height);
	}

	public boolean applyDamageAtSide(RayTraceResult.EnumEdgeSide side) {
		return attackall ? true : side == RayTraceResult.EnumEdgeSide.RIGHT;
	}

	@Override
	public void drawEntity() {
		GL11.glPushMatrix();
		CERenderEngine.bindTexture("edge");
		RenderUtils.renderTexturedQuads(getGeomProps());

		Rect pos = getGeomProps();
		GL11.glTranslated(this.offsetX, this.offsetY, 0.0D);
		CERenderEngine.bindTexture(this.textureID);
		RenderUtils.renderTexturedQuads(
				this.drawRect.getMinX() + pos.getMinX(),
				this.drawRect.getMinY() + pos.getMinY(),
				this.drawRect.getMaxX() + pos.getMinX(),
				this.drawRect.getMaxY() + pos.getMinY(),
				this.mapping.getMinX(), this.mapping.getMinY(),
				this.mapping.getMaxX(), this.mapping.getMaxY());
		GL11.glPopMatrix();
	}
}