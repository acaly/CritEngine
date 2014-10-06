/**
 * 
 */
package cn.weathfold.demo.menu;

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
public class EntityTitle extends Entity {
	
	public static final int
		WIDTH = 256,
		HEIGHT = 72;
	
	public EntityTitle(SceneMenu scene) {
		super(scene, 0, 512 - HEIGHT, WIDTH, HEIGHT);
		this.setTexture(SceneMenu.TEX_TITLE);
	}
	
	public void drawEntity() {
		CERenderEngine.bindTexture(textureID);
		GL11.glPushMatrix(); {
			float alpha = 0.2F + 0.4F * (1 + (float)Math.sin(CritEngine.getVirtualTime() / 1000D));
			GL11.glColor4f(1F, 1F, 1F, alpha);
			RenderUtils.renderTexturedQuads(getGeomProps());
		} GL11.glPopMatrix();
	}

}
