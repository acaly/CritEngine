/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;

import org.lwjgl.util.WaveData;

/**
 * @author WeAthFolD
 *
 */
public class WavSoundObject implements SoundObject {
	
	WaveData wave;
	
	public WavSoundObject(String path) {
		wave = WaveData.create(this.getClass().getResource(path));
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.resource.SoundObject#getBuffer()
	 */
	@Override
	public ByteBuffer getBuffer() {
		return wave.data;
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.resource.SoundObject#getFormat()
	 */
	@Override
	public int getFormat() {
		return wave.format;
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.resource.SoundObject#getSamplerFreq()
	 */
	@Override
	public int getSamplerFreq() {
		return wave.samplerate;
	}

}
