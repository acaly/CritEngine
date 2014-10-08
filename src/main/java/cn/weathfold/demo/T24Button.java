package cn.weathfold.demo;

import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;

/**
 * 通用的按钮基类
 * @author WeAthFolD
 */
public class T24Button extends GUIButton {
	
	private boolean hovering = true; //鼠标是否在按钮上
	String texHovering; //鼠标在按钮上的贴图
	String texNormal; //正常状态的贴图

	public T24Button(Scene scene, double x, double y, double width,
			double height, String texture0, String texture1) {
		super(scene, x, y, width, height);
		
		this.texNormal = texture0;
		this.texHovering = texture1;
		setTexture(this.texNormal);
	}

	@Override
	public void onFrameUpdate() {
		boolean b = isMouseInEntity();
		if ((!this.hovering) && (b)) {
			CESoundEngine.playGlobalSound(Type24.SND_BUTTON_CLICK, new SoundAttributes(100));
		}
		this.hovering = b;

		super.onFrameUpdate();
	}

	@Override
	public void onButtonPressed() { }

	@Override
	public void onButtonFrame() { }

	@Override
	public void onButtonReleased() {
		CESoundEngine.playGlobalSound(Type24.SND_BUTTON_RELEASE, new SoundAttributes(300));
	}

	@Override
	public void drawEntity() {
		this.textureID = (this.hovering ? this.texHovering : this.texNormal);
		super.drawEntity();
	}
}