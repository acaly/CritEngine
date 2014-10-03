package cn.weathfold.critengine.camera;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.weathfold.critengine.CEDebugger;
import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

public class Camera {
	
	/**
	 * 摄像机大小的对齐方式（width不变 or height不变）
	 */
	public enum Alignment {
		NONE, ALIGN_WIDTH, ALIGN_HEIGHT
	};
	
	protected Rect camRect; //相机区域
	protected Alignment align;
	protected final Scene scene;
	
	public Camera(Scene scene) {
		this.scene = scene;
	}
	
	public Camera(Scene scene, double x, double y, double width, double height, Alignment align) {
		this(scene);
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

		CEDebugger.fine("Created camera at " + x + ", " + y +
				" with size " + width + "," + height);
		 
	}
	
	public Camera setPosition(double x, double y) {
		this.camRect.pos.x = x;
		this.camRect.pos.y = y;
		return this;
	}
	
	public void addPosition(double x, double y) {
		this.camRect.pos.addVector(x, y);
		
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
	
	/**
	 * 绘制摄像机范围内部的所有物体，包括Scene和Entity。
	 */
	public void draw() {
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		//平移到该Camera原点
		double scale = (double)this.camRect.width / Display.getWidth();
		GL11.glScaled(scale, scale, 1F);
		GL11.glTranslated(-this.camRect.getMinX(), -this.camRect.getMinY(), 0D);
		
		scene.renderScene(); 
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
