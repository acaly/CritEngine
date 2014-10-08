package cn.weathfold.critengine.resource;

import cn.weathfold.critengine.CEDebugger;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class ResourcePool {
	
	Map<String, Integer> textureMap = new HashMap<String, Integer>(); //贴图表
	Map<String, Integer> soundMap = new HashMap<String, Integer>(); //声音表

	/**
	 * 预加载声音
	 * @param obj 声音内容
	 * @param key 注册的声音ID
	 */
	public void preloadSound(SoundObject obj, String key) {
		int buf = AL10.alGenBuffers();
		try {
			AL10.alBufferData(buf, obj.getFormat(), obj.getBuffer(),
					obj.getSamplerFreq());
			this.soundMap.put(key, Integer.valueOf(buf));
			CEDebugger.fine("Preloaded sound " + key);
		} catch (Exception e) {
			CEDebugger.error("An error occured when preloading sound " + key
					+ ", stackTrace : ");
			CEDebugger.error(e.toString());
		}
	}
	
	/**
	 * 预加载贴图文件
	 * @param obj 贴图内容
	 * @param key 要注册到的id
	 */
	public void preloadTexture(TextureObject obj, String key) {
		try {
			int texID = GL11.glGenTextures();

			GL13.glActiveTexture(33984);
			GL11.glBindTexture(3553, texID);

			GL11.glPixelStorei(3317, 1);

			GL11.glTexImage2D(3553, 0, 6408, obj.getWidth(), obj.getHeight(),
					0, 6408, 5121, obj.getBuffer());
			GL30.glGenerateMipmap(3553);

			GL11.glTexParameteri(3553, 10242, 10497);
			GL11.glTexParameteri(3553, 10243, 10497);

			GL11.glTexParameteri(3553, 10240, 9728);
			GL11.glTexParameteri(3553, 10241, 9987);

			this.textureMap.put(key, Integer.valueOf(texID));

			CEDebugger.fine("Preloaded texture " + key + ", size "
					+ obj.getWidth() + "x" + obj.getHeight());
		} catch (Exception e) {
			CEDebugger.error("An error occured when preloading texture " + key
					+ ", stackTrace : ");
			CEDebugger.error(e.toString());
		}
	}

	/**
	 * 释放该池的所有资源。
	 */
	public void free() {
		for (int i : textureMap.values()) {
			GL11.glDeleteTextures(i);
		}

		for (int i : soundMap.values()) {
			AL10.alDeleteBuffers(i);
		}
	}

	/**
	 * 预加载贴图列表
	 * @param obj 贴图内容列表
	 * @param key 总key
	 * @return 生成的key序列
	 */
	public String[] preloadTextureArray(TextureObject[] obj, String key) {
		String[] keys = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			String k = key + i;
			preloadTexture(obj[i], k);
			keys[i] = k;
		}
		return keys;
	}

	/**
	 * 预加载声音列表
	 * @param obj 声音内容列表
	 * @param key 总key
	 * @return 生成的key序列
	 */
	public String[] preloadSoundArray(SoundObject[] obj, String key) {
		String[] keys = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			String k = key + i;
			preloadSound(obj[i], k);
			keys[i] = k;
		}
		return keys;
	}

	protected int getTexture(String key) {
		return this.textureMap.containsKey(key) ? this.textureMap
				.get(key).intValue() : 0;
	}

	protected int getSound(String key) {
		return this.soundMap.containsKey(key) ? this.soundMap
				.get(key).intValue() : 0;
	}
}