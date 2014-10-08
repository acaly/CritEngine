package cn.weathfold.demo.mainmenu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import org.lwjgl.opengl.GL11;

/**
 * 主界面标题实体
 * @author WeAthFolD
 */
public class EntityTitle extends Entity {
	
	public static final int 
		WIDTH = 256,
		HEIGHT = 72;

	public EntityTitle(SceneMainMenu scene) {
		super(scene, 0.0D, 440.0D, WIDTH, HEIGHT);
		setTexture(SceneMainMenu.TEX_TITLE);
	}

	@Override
	public void drawEntity() {
		CERenderEngine.bindTexture(this.textureID);
		GL11.glPushMatrix(); {
			//正弦模拟的周期透明度
			float alpha = 0.2F + 0.4F * (1.0F + (float) Math.sin(CritEngine.getVirtualTime() / 1000.0D));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
			RenderUtils.renderTexturedQuads(getGeomProps());
		} GL11.glPopMatrix();
	}
}