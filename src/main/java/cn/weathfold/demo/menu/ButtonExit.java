/**
 * 
 */
package cn.weathfold.demo.menu;

import org.lwjgl.opengl.Display;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class ButtonExit extends GUIButton {

	private String 
		NORMAL = SceneMenu.TEX_QUIT,
		ACTIVATED = SceneMenu.TEX_QUIT_ACTIVATED;
	
	/**
	 * @param scene
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ButtonExit(Scene scene) {
		super(scene, 10, 12, 220, 40);
		this.setTexture(NORMAL);
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.entity.gui.GUIButton#onButtonPressed()
	 */
	@Override
	public void onButtonPressed() {
		CritEngine.switchScene(null);
	}
	
	@Override
	public void drawEntity() {
		this.textureID = this.isMouseInEntity() ? ACTIVATED : NORMAL;
		super.drawEntity();
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.entity.gui.GUIButton#onButtonFrame()
	 */
	@Override
	public void onButtonFrame() {}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.entity.gui.GUIButton#onButtonReleased()
	 */
	@Override
	public void onButtonReleased() {}

}
