/**
 * 
 */
package cn.weathfold.critengine.render;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.util.Rect;

/**
 * @author WeAthFolD
 *
 */
public class RenderUtils {
	
	public static void renderTexturedQuads(Rect sceneRect) {
		renderTexturedQuads(sceneRect, 0, 0, 1, 1);
	}

	public static void renderTexturedQuads(Rect sceneRect, double u0, double v0, double u1, double v1) {
		renderTexturedQuads(sceneRect.getMinX(), sceneRect.getMinY(), sceneRect.getMaxX(), sceneRect.getMaxY(),
				u0, v0, u1, v1);
	}
	
	public static void renderTexturedQuads(double x0, double y0, double x1, double y1) {
		renderTexturedQuads(x0, y0, x1, y1, 0, 0, 1, 1);
	}
	
	public static void renderTexturedQuads(double x0, double y0, double x1, double y1,
			double u0, double v0, double u1, double v1) {
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2d(u0, v0); GL11.glVertex2d(x0, y1);
		GL11.glTexCoord2d(u0, v1); GL11.glVertex2d(x0, y0);
		GL11.glTexCoord2d(u1, v1); GL11.glVertex2d(x1, y0);
		GL11.glTexCoord2d(u1, v0); GL11.glVertex2d(x1, y1);
		
		GL11.glEnd();
	}
}
