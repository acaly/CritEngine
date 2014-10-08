package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.RayTraceResult.EnumEdgeSide;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import org.lwjgl.opengl.GL11;

public class EntityObstacle extends Entity {
	public double zHeight;
	public int dps;
	public double offsetX;
	public double offsetY;
	public Rect drawRect;
	public boolean collided = false;

	public EntityObstacle(Scene scene, double x, double y, double width,
			double height, String texture, double zHeight, int dps,
			boolean hit, double offX, double offY, double drawWidth,
			double drawHeight) {
		super(scene, x, y, width, height);
		this.zHeight = zHeight;
		this.dps = dps;
		setTexture(texture);
		this.offsetX = offX;
		this.offsetY = offY;
		this.drawRect = new Rect(0.0D, 0.0D, drawWidth, drawHeight);
		if (hit)
			addAttribute(new AttrCollider() {
				public boolean onCollided(RayTraceResult res) {
					return false;
				}
			});
	}

	public EntityObstacle(Scene scene, double x, double y, double width,
			double height) {
		super(scene, x, y, width, height);
	}

	public boolean applyDamageAtSide(RayTraceResult.EnumEdgeSide side) {
		return side == RayTraceResult.EnumEdgeSide.RIGHT;
	}

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