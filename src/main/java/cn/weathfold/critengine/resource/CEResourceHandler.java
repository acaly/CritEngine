/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.SoundEmitter;

/**
 * @author WeAthFolD
 *
 */
public class CEResourceHandler {

	private static Map<Scene, ResourcePool> srcPool = new HashMap<Scene, ResourcePool>();
	private static ResourcePool constantPool = new ResourcePool();
	
	public static ResourcePool allocatePool(Scene scene) {
		if(srcPool.containsKey(scene))
			return null;
		ResourcePool pool = new ResourcePool();
		srcPool.put(scene, pool);
		return pool;
	}
	
	public static void freeResourcePool(Scene scene) {
		ResourcePool rp = srcPool.remove(scene);
		rp.free();
	}
	
	public static void globalPreloadTexture(TextureObject obj, String key) {
		constantPool.preloadTexture(obj, key);
	}
	
	public static int queryTextureId(String key) {
		Scene sc = CritEngine.getCurrentScene();
		int id;
		if(sc != null) {
			id = srcPool.get(sc).getTexture(key);
			if(id != 0) {
				return id;
			}
		}
		id = constantPool.getTexture(key);
		return id;
	}
	
	public static int querySoundId(String key) {
		Scene sc = CritEngine.getCurrentScene();
		if(sc != null) {
			int id = srcPool.get(sc).getSound(key);
			if(id != 0)
				return id;
		}
		return constantPool.getSound(key);
	}
	
}
