/**
 * 
 */
package cn.weathfold.critengine.render;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class CEResourceHandler {

	private static Map<Scene, ResourcePool> srcPool = new HashMap<Scene, ResourcePool>();
	private static ResourcePool constantPool = new ResourcePool();
	
	public static void preloadTexture(TextureObject obj, String key) {
		constantPool.preloadTexture(obj, key);
	}
	
	public static void bindTexture(String key) {
		Scene sc = CritEngine.getCurrentScene();
		int id;
		if(sc != null) {
			id = srcPool.get(sc).getTexture(key);
			if(id != 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
				return;
			}
		}
		id = constantPool.getTexture(key);
		if(id != 0) {
			System.out.println("Bind Texture From Constant Pool");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		}
	}
	
	
}
