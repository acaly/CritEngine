/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.SceneGame;

/**
 * 
 * @author WeAthFolD
 *
 */
public class EntityObstacle extends Entity {
	
	public final double zHeight; //虚拟高度
	public final double dps; //每秒伤害输出
	public final double offsetX, offsetY;
	public final Rect drawRect;

	public EntityObstacle(Scene scene, double x, double y, double width,
			double height, String texture, double zHeight, double dps, boolean hit, 
			double offX, double offY, double drawWidth, double drawHeight) {
		super(scene, x, y, width, height);
		this.zHeight = zHeight;
		this.dps = dps;
		this.setTexture(texture);
		offsetX = offX;
		offsetY = offY;
		drawRect = new Rect(0, 0, drawWidth, drawHeight);
		if(hit)
		this.addAttribute(new AttrCollider() {
			{
				doesMove = false;
			}
		});
	}
	
	public void drawEntity() {
		GL11.glPushMatrix(); {
			CERenderEngine.bindTexture(SceneGame.TEX_EDGE);
			RenderUtils.renderTexturedQuads(getGeomProps());
			
			Rect pos = this.getGeomProps();
			GL11.glTranslated(offsetX, offsetY, 0);
			CERenderEngine.bindTexture(textureID);
			RenderUtils.renderTexturedQuads(drawRect.getMinX() + pos.getMinX(), drawRect.getMinY() + pos.getMinY(),
					drawRect.getMaxX() + pos.getMinX(), drawRect.getMaxY() + pos.getMinY(),
					mapping.getMinX(), mapping.getMinY(), mapping.getMaxX(), mapping.getMaxY());
		} GL11.glPopMatrix();
	}

}
