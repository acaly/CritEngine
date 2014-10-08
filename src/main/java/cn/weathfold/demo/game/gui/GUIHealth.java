/**
 * 
 */
package cn.weathfold.demo.game.gui;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.entity.gui.GUIComponent;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 玩家血条显示的GUI
 * @author WeAthFolD
 */
public class GUIHealth extends GUIComponent {

	private static final double LEN_PRG = 240, OFFESETX = 26;

	private EntityPlayer player;

	public GUIHealth(SceneGame scene) {
		super(scene, 470, 15, 360, 84);
		player = scene.thePlayer;
	}

	@Override
	public int getRenderPriority() {
		return 3;
	}

	@Override
	public void drawEntity() {
		int health = (int) player.getHealth();
		double len = LEN_PRG * (health / 100D); // 条的长度
		GL11.glPushMatrix(); {
			
			GL11.glTranslated(470, 15, 0);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			CERenderEngine.bindTexture(SceneGame.TEX_HPBAR);
			RenderUtils.renderTexturedQuads(0, 0, 360, 84);

			String tex;
			if (player.isAttacked()) {
				tex = SceneGame.TEX_HP[3];
			} else {
				tex = SceneGame.TEX_HP[(100 - health) / 34];
			}
			CERenderEngine.bindTexture(tex);
			RenderUtils.renderTexturedQuads(LEN_PRG + OFFESETX - len, 0, 360,
					84, (LEN_PRG + OFFESETX - len) / 360, 0, 1, 1);

		} GL11.glPopMatrix();
	}

}
