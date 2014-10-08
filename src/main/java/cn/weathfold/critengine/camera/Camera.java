package cn.weathfold.critengine.camera;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Vector2d;

/**
 * 摄像机类。摄像机接管的是场景中实体的绘制工作，它一般在Scene中的renderForeground()被调用
 * @author WeAthFolD
 */
public class Camera extends Entity {

	protected Alignment align;

	public Camera(Scene scene, double x, double y) {
		super(scene, x, y);
	}

	public Camera(Scene scene, double x, double y, double width, double height,
			Alignment align) {
		super(scene, x, y, width, height);
		this.align = align;
		refreshStat();
	}

	/**
	 * （在尺寸改变等时）刷新摄像机状态。
	 */
	public void refreshStat() {
		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		switch (align) {
		case ALIGN_HEIGHT:
			attr.setWidth(attr.getHeight() * CritEngine.getAspectRatio());
			break;
		case ALIGN_WIDTH:
			attr.setHeight(attr.getWidth() / CritEngine.getAspectRatio());
			break;
		default:
			break;
		}
	}

	/**
	 * 获取当前的鼠标位置
	 */
	public final Vector2d getMouseLocation() {
		double mx = Mouse.getX(), my = Mouse.getY();
		AttrGeometry pos = (AttrGeometry) this.getAttribute("geometry");
		double scale = pos.getWidth() / Display.getWidth();
		mx *= scale;
		my *= scale;
		return new Vector2d(mx + pos.getMinX(), my + pos.getMinY());
	}

	/**
	 * 绘制摄像机范围内部的所有物体，包括Scene和Entity。
	 */
	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		AttrGeometry attr = (AttrGeometry) this.getAttribute("geometry");
		// 平移到该Camera原点
		double scale = Display.getWidth() / attr.getWidth();
		GL11.glScaled(scale, scale, 1F);
		GL11.glTranslated(-attr.getMinX(), -attr.getMinY(), 0D);

		List<Entity> list = this.sceneObj.getRenderEntityList();
		for (Entity e : list) {
			if (doesDrawEntity(e))
				e.drawEntity();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	/**
	 * 是否对某个实体进行绘制？（默认只绘制屏幕内）
	 */
	protected boolean doesDrawEntity(Entity e) {
		return e.getGeomProps().intersects(getGeomProps());
	}
	
	/**
	 * 摄像机大小的对齐方式（width不变 or height不变）
	 */
	public enum Alignment {
		NONE, ALIGN_WIDTH, ALIGN_HEIGHT
	};
}
