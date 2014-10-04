/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

/**
 * @author WeAthFolD
 *
 */
public class ResourcePool {
	
	Map<String, Integer> textureMap = new HashMap<String, Integer>();
	Map<String, Integer> soundMap = new HashMap<String, Integer>();
	
	public void preloadSound(SoundObject obj, String key) {
		int buf = AL10.alGenBuffers();
		AL10.alBufferData(buf, obj.getFormat(), obj.getBuffer(), obj.getSamplerFreq());
		soundMap.put(key, buf);
	}
	
	public void free() {
		//释放贴图
		for(int i : textureMap.values()) {
			GL11.glDeleteTextures(i);
		}
		
		//释放声音
		for(int i : soundMap.values()) {
			AL10.alDeleteBuffers(i);
		}
	}
	
	/* 加载一个特定的贴图资源，之后可以通过key来重新绑定它 */
	public void preloadTexture(TextureObject obj, String key) {
		int texID = GL11.glGenTextures();
		//准备工作
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		//上载贴图信息
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB,
				obj.getWidth(), obj.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, obj.getBuffer());
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		//设置UV平铺方式
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		//设置缩放参数
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		
		textureMap.put(key, texID);
	}
	
	public int getTexture(String key) {
		return textureMap.get(key);
	}
	
	public int getSound(String key) {
		return soundMap.get(key);
	}
	
}
