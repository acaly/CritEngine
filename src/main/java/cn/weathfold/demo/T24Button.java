/**
 * 
 */
package cn.weathfold.demo;

import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;

/**
 * @author WeAthFolD
 *
 */
public class T24Button extends GUIButton {
	
	private boolean hovering = true;

	/**
	 * @param scene
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public T24Button(Scene scene, double x, double y, double width,
			double height) {
		super(scene, x, y, width, height);
	}
	
	public void onFrameUpdate() {
		boolean b = this.isMouseInEntity();
		if(!hovering && b) {
			CESoundEngine.playGlobalSound(Type24.SND_BUTTON_CLICK, new SoundAttributes(300));
		}
		hovering = b;
		super.onFrameUpdate();
	}

	@Override
	public void onButtonPressed() {
		
	}

	@Override
	public void onButtonFrame() {
	}

	@Override
	public void onButtonReleased() {
		CESoundEngine.playGlobalSound(Type24.SND_BUTTON_RELEASE, new SoundAttributes(300));
	}

}
