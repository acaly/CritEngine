/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;

/**
 * 预载入贴图时用的临时对象，可以传递给ResourcePool进行加载。
 * @author WeAthFolD
 */
public interface TextureObject {
	
	/* 获取该贴图对应的Buffer */
	ByteBuffer getBuffer();
	
	/* 获取贴图宽度 */
	int getWidth();
	
	/* 获取贴图高度 */
	int getHeight();
	
}
