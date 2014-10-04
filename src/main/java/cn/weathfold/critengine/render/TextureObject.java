/**
 * 
 */
package cn.weathfold.critengine.render;

import java.nio.ByteBuffer;

/**
 * @author WeAthFolD
 *
 */
public interface TextureObject {
	
	ByteBuffer getBuffer();
	int getWidth();
	int getHeight();
	
}
