package cn.weathfold.demo.game.gui;

import org.newdawn.slick.Color;

import cn.weathfold.critengine.entity.gui.GUIComponent;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.SceneGame;

/**
 * 分数显示GUI
 * @author WeAthFolD
 */
public class InfoScore extends GUIComponent {
	
	final Color color = new Color(162, 134, 81, 200);
	
	public InfoScore(Scene scene) {
		super(scene, 470.0D, 15.0D, 360.0D, 84.0D);
	}
	
	@Override
	public void drawEntity() {
		String score = String.valueOf(((SceneGame)sceneObj).calculateScore());
		float size = 32.0F;
		CERenderEngine.switchFont("Copperplate Gothic Bold");
		CERenderEngine.drawString(25, 512 - 20, "SCORE " + score, size, this.color);
	}
}
