package cn.weathfold.demo.game.gui;

import cn.weathfold.critengine.entity.gui.GUIComponent;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.demo.game.SceneGame;
import org.newdawn.slick.Color;

/**
 * 弹药显示GUI
 * @author WeAthFolD
 */
public class InfoAmmo extends GUIComponent {
	
	final Color color = new Color(255, 255, 255, 200);
	SceneGame sceneGame;

	public InfoAmmo(SceneGame scene) {
		super(scene, 470.0D, 15.0D, 360.0D, 84.0D);
		this.sceneGame = scene;
	}

	@Override
	public void drawEntity() {
		String ammo = "AMMO:" + this.sceneGame.thePlayer.getAmmo();
		float size = 32.0F;
		CERenderEngine.switchFont("Copperplate Gothic Bold");
		double len = CERenderEngine.getStringLength(ammo, size);
		CERenderEngine.drawString(799.0D - len, 130.0D, ammo, size, this.color);
	}
}