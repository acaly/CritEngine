package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;

/**
 * 声音预加载所用到的临时类
 * @author WeAthFolD
 */
public interface SoundObject {
	int getFormat();

	int getSamplerFreq();

	ByteBuffer getBuffer();
}
