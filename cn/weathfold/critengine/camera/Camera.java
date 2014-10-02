package cn.weathfold.critengine.camera;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.util.Rect;

public class Camera {
	
	/**
	 * ��ͷ��С�Ķ��뷽ʽ��width���� or height���䣩
	 */
	public enum Alignment {
		NONE, ALIGN_WIDTH, ALIGN_HEIGHT
	};
	
	protected Rect camRect; //�������
	protected Alignment align;
	
	public Camera(double x, double y, double width, double height, Alignment align) {
		this.align = align;
		
		switch(align) {
		case ALIGN_HEIGHT:
			width = height * CritEngine.getAspectRatio();
			break;
		case ALIGN_WIDTH:
			height = width / CritEngine.getAspectRatio();
			break;
		default:
			break;
		}
		
		camRect = new Rect(x, y, width, height);
	}
	
	public void refreshStat() {
		switch(align) {
		case ALIGN_HEIGHT:
			camRect.width = camRect.width * CritEngine.getAspectRatio();
			break;
		case ALIGN_WIDTH:
			camRect.height = camRect.width / CritEngine.getAspectRatio();
			break;
		default:
			break;
		}
	}
}
