/**
 * 
 */
package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.game.SceneGame;

/**
 * @author WeAthFolD
 *
 */
public class ButtonStart extends T24Button {
	
	private String 
		NORMAL = SceneMenu.TEX_START,
		ACTIVATED = SceneMenu.TEX_START_ACTIVATED;

	/**
	 * @param scene
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ButtonStart(Scene scene) {
		super(scene, 10, 52, 220, 40);
		this.setTexture(NORMAL);
	}
	
	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.entity.gui.GUIButton#onButtonReleased()
	 */
	@Override
	public void onButtonReleased() {
		super.onButtonReleased();
		CritEngine.switchScene(new SceneGame());
	}
	
	@Override
	public void drawEntity() {
		this.textureID = this.isMouseInEntity() ? ACTIVATED : NORMAL;
		super.drawEntity();
	}

}
