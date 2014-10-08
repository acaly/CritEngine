/**
 * 
 */
package cn.weathfold.demo.game.misc;

import java.util.Set;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundEmitter;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.obstacle.EntityObstacle;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 可拾取道具的基类
 * @author WeAthFolD
 */
public abstract class EntityPickable extends EntityObstacle {
	
	public EntityPickable(Scene scene, double x, double y, String texture) {
		super(scene, x, y, 38.0D, 34.0D);
		this.textureID = texture;
	}
	
	public abstract void onPlayerInteraction(EntityPlayer player);

	@Override
	public void onFrameUpdate() {
		Set<Entity> entities = this.sceneObj.getEntitiesWithin(getGeomProps(),
				PlayerFilter.INSTANCE, new Entity[0]);
		
		if (!entities.isEmpty()) {
			EntityPlayer player = (EntityPlayer) entities.iterator().next();
			onPlayerInteraction(player);
			this.deathFlag = true;
		}
	}

	@Override
	public void drawEntity() {
		GL11.glPushMatrix();
		AttrGeometry geom = getGeomProps();
		CERenderEngine.bindTexture("pshadow");
		double xOffset = -5.0D;
		double yOffset = -11.0D;
		double size = 50.0D;

		double x0 = geom.getMinX() + xOffset;
		double y0 = geom.getMinY() + xOffset;
		double x1 = x0 + size;
		double y1 = y0 + size;
		RenderUtils.renderTexturedQuads(x0, y0, x1, y1);
		GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			yOffset = 5.0D * Math.sin(CritEngine.getVirtualTime() / 1000.0D);
			GL11.glTranslated(0.0D, yOffset, 0.0D);
			CERenderEngine.bindTexture(textureID);
			RenderUtils.renderTexturedQuads(getGeomProps());
		} GL11.glPopMatrix();
	}

	public static class Template implements IEntityTemplate {
		public Scene scene;

		public Template(Scene scene) {
			this.scene = scene;
		}

		@Override
		public void generate(double x, double y) {
			this.scene.spawnEntity(new EntityAmmo(this.scene, x, y));
		}
	}

}
