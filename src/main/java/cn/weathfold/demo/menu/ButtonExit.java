/**
 * 
 */
package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;

/**
 * @author WeAthFolD
 *
 */
public class ButtonExit extends T24Button {

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
	
	@Override
	public void drawEntity() {
		this.textureID = this.isMouseInEntity() ? ACTIVATED : NORMAL;
		super.drawEntity();
	}

	@Override
	public void onButtonFrame() {}

	@Override
	public void onButtonReleased() {
		CritEngine.switchScene(null);
	}

}
