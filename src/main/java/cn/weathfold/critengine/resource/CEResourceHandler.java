/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.util.HashMap;
import java.util.Map;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;

/**
 * 全局的资源控制器，接管资源加载等等
 * @author WeAthFolD
 */
public class CEResourceHandler {

	private static Map<Scene, ResourcePool> srcPool = new HashMap<Scene, ResourcePool>(); //场景资源 池
	private static ResourcePool constantPool = new ResourcePool(); //全局资源池

	/**
	 * 为某个场景分配资源池，如果存在则返回已有的
	 */
	public static ResourcePool allocatePool(Scene scene) {
		if (srcPool.containsKey(scene))
			return null;
		ResourcePool pool = new ResourcePool();
		srcPool.put(scene, pool);
		return pool;
	}

	/**
	 * 释放资源池
	 * @param scene
	 */
	public static void freeResourcePool(Scene scene) {
		ResourcePool rp = srcPool.remove(scene);
		rp.free();
		System.gc(); //真的有用么
	}

	/**
	 * 在全局资源池里预加载贴图
	 */
	public static void globalPreloadTexture(TextureObject obj, String key) {
		constantPool.preloadTexture(obj, key);
	}

	/**
	 * 在全局资源池里预加载声音
	 */
	public static void globalPreloadSound(SoundObject obj, String key) {
		constantPool.preloadSound(obj, key);
	}

	/**
	 * 查询某个贴图的GL贴图号
	 * @param key 贴图id
	 * @return 对应的GL贴图id，如果不存在返回的是0
	 */
	public static int queryTextureId(String key) {
		Scene sc = CritEngine.getCurrentScene();
		int id;
		if (sc != null) {
			id = srcPool.get(sc).getTexture(key);
			if (id != 0) {
				return id;
			}
		}
		id = constantPool.getTexture(key);
		return id;
	}

	/**
	 * 查询某个声音对应的AL内部id
	 * @param key 声音id
	 * @return 对应的AL内部id，如果不存在返回0
	 */
	public static int querySoundId(String key) {
		Scene sc = CritEngine.getCurrentScene();
		if (sc != null) {
			int id = srcPool.get(sc).getSound(key);
			if (id != 0)
				return id;
		}
		return constantPool.getSound(key);
	}

}
