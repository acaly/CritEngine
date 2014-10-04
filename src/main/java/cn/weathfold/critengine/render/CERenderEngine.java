/**
 * 
 */
package cn.weathfold.critengine.render;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.resource.CEResourceHandler;

/**
 * @author WeAthFolD
 *
 */
public class CERenderEngine {
	
	public static void bindTexture(String key) {
		int id = CEResourceHandler.queryTextureId(key);
		if(id != 0)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
}
