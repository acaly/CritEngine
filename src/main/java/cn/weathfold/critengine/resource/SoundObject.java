package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;

public interface SoundObject {
	int getFormat();
	int getSamplerFreq();
	ByteBuffer getBuffer();
}
