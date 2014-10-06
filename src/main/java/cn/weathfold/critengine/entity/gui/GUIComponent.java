/**
 * 
 */
package cn.weathfold.critengine.entity.gui;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Vector2d;

/**
 * GUI的基类，内置了键位侦听的支持。往guiListener里添加键位侦听即可在侦听到按键事件~
 * @author WeAthFolD
 */
public abstract class GUIComponent extends Entity {
	
	/* 全部delegate给gui类 */
	protected KeyEventProducer guiListener = new KeyEventProducer() {

		@Override
		public void onKeyDown(int kid) {
			GUIComponent.this.onKeyDown(kid);
		}

		@Override
		public void onKeyFrame(int kid) {
			GUIComponent.this.onKeyFrame(kid);
		}

		@Override
		public void onKeyUp(int kid) {
			GUIComponent.this.onKeyUp(kid);
		}
		
	};

	public GUIComponent(Scene scene, double x, double y, double width, double height) {
		super(scene, x, y, width, height);
	}
	
	@Override
	public void onFrameUpdate() {
		guiListener.frameUpdate();
	}
	
	/* 判断鼠标是否在实体内部  */
	protected boolean isMouseInEntity() {
		AttrGeometry pos = (AttrGeometry) this.getAttribute("geometry");
		Vector2d mp = this.sceneObj.mainCamera.getOffsetedMouseLocation();
		return (pos.getMinX() <= mp.x && mp.x <= pos.getMaxX()) && 
				(pos.getMinY() <= mp.y && mp.y <= pos.getMaxY());
	}
	
	protected void onKeyDown(int kid) {
		if(!isMouseInEntity())
			return;
	}
	
	protected void onKeyFrame(int kid) {
		if(!isMouseInEntity())
			return;
	}
	
	protected void onKeyUp(int kid) {
		if(!isMouseInEntity())
			return;
	}

}
