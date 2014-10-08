package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;
import org.lwjgl.util.WaveData;

/**
 * WAV声音加载器
 * @author WeAthFolD
 */
public class WavSoundObject implements SoundObject {
	WaveData wave;

	public WavSoundObject(String path) {
		this.wave = WaveData.create(getClass().getResource(path));
	}

	@Override
	public ByteBuffer getBuffer() {
		return this.wave.data;
	}

	@Override
	public int getFormat() {
		return this.wave.format;
	}

	@Override
	public int getSamplerFreq() {
		return this.wave.samplerate;
	}

	public static SoundObject[] readArray(String[] arr) {
		SoundObject[] res = new SoundObject[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = new WavSoundObject(arr[i]);
		}
		return res;
	}
}