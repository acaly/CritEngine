/**
 * 
 */
package cn.weathfold.demo.over;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class EntityInfo extends Entity {

	/**
	 * @param scene
	 */
	public EntityInfo(Scene scene) {
		super(scene, 200, 0, 413, 43);
		this.setTexture(SceneGameOver.TEX_INFO);
	}

	public void drawEntity() {
		GL11.glPushMatrix(); {
			float alpha = 0.2F + 0.4F * (1 + (float)Math.sin(CritEngine.getVirtualTime() / 1000D));
			GL11.glColor4f(1F, 1F, 1F, alpha);
			CERenderEngine.bindTexture(textureID);
			RenderUtils.renderTexturedQuads(this.getGeomProps(),
					mapping.getMinX(), mapping.getMinY(), mapping.getMaxX(), mapping.getMaxY());
			GL11.glColor4f(1F, 1F, 1F, 1F);
		} GL11.glPopMatrix();
	}
	
}
