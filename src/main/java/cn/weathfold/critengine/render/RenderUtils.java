package cn.weathfold.critengine.render;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.util.Rect;

/**
 * 一些渲染辅助函数。
 * @author WeAthFolD
 */
public class RenderUtils {

	/**
	 * 绘制贴图矩形，映射整张贴图
	 * @param sceneRect
	 */
	public static void renderTexturedQuads(Rect sceneRect) {
		renderTexturedQuads(sceneRect, 0, 0, 1, 1);
	}

	/**
	/**
	 * 绘制贴图矩形
	 * @param sceneRect 对应矩形坐标的Rect
	 * @param u0 u最小
	 * @param v0 v最小
	 * @param u1 u最大
	 * @param v1 v最大
	 */
	public static void renderTexturedQuads(Rect sceneRect, double u0,
			double v0, double u1, double v1) {
		renderTexturedQuads(sceneRect.getMinX(), sceneRect.getMinY(),
				sceneRect.getMaxX(), sceneRect.getMaxY(), u0, v0, u1, v1);
	}

	/**
	 * 绘制贴图矩形，贴图的映射范围为整张贴图
	 * @param x0 x最小
	 * @param y0 y最小
	 * @param x1 x最大
	 * @param y1 y最大
	 */
	public static void renderTexturedQuads(double x0, double y0, double x1,
			double y1) {
		renderTexturedQuads(x0, y0, x1, y1, 0, 0, 1, 1);
	}

	/**
	 * 绘制贴图矩形
	 * @param x0 x最小
	 * @param y0 y最小
	 * @param x1 x最大
	 * @param y1 y最大
	 * @param u0 u最小
	 * @param v0 v最小
	 * @param u1 u最大
	 * @param v1 v最大
	 */
	public static void renderTexturedQuads(double x0, double y0, double x1,
			double y1, double u0, double v0, double u1, double v1) {
		GL11.glBegin(GL11.GL_QUADS); {
			GL11.glTexCoord2d(u0, v0);
			GL11.glVertex2d(x0, y1);
			GL11.glTexCoord2d(u0, v1);
			GL11.glVertex2d(x0, y0);
			GL11.glTexCoord2d(u1, v1);
			GL11.glVertex2d(x1, y0);
			GL11.glTexCoord2d(u1, v0);
			GL11.glVertex2d(x1, y1);
		} GL11.glEnd();
	}
}
