package cn.weathfold.critengine.camera;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.weathfold.critengine.CEDebugger;
import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

public class Camera extends Entity {
	
	/**
	 * 摄像机大小的对齐方式（width不变 or height不变）
	 */
	public enum Alignment {
		NONE, ALIGN_WIDTH, ALIGN_HEIGHT
	};
	
	protected Alignment align;
	
	public Camera(Scene scene, double x, double y) {
		super(scene, x, y);
	}
	
	public Camera(Scene scene, double x, double y, double width, double height, Alignment align) {
		super(scene, x, y, width, height);
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

		CEDebugger.fine("Created camera at " + x + ", " + y +
				" with size " + width + "," + height);
		 
	}
	
	public Camera setPosition(double x, double y) {
		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		attr.pos.x = x;
		attr.pos.y = y;
		return this;
	}
	
	public void addPosition(double x, double y) {
		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		attr.pos.addVector(x, y);
	}
	
	public void refreshStat() {
		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		switch(align) {
		case ALIGN_HEIGHT:
			attr.width = attr.width * CritEngine.getAspectRatio();
			break;
		case ALIGN_WIDTH:
			attr.height = attr.width / CritEngine.getAspectRatio();
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
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		//平移到该Camera原点
		double scale = (double)attr.width / Display.getWidth();
		GL11.glScaled(scale, scale, 1F);
		GL11.glTranslated(-attr.getMinX(), -attr.getMinY(), 0D);
		
		sceneObj.renderScene(); 
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
