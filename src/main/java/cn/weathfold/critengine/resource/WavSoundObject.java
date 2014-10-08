package cn.weathfold.critengine.resource;

import java.nio.ByteBuffer;
import org.lwjgl.util.WaveData;

public class WavSoundObject
  implements SoundObject
{
  WaveData wave;

  public WavSoundObject(String path)
  {
    this.wave = WaveData.create(getClass().getResource(path));
  }

  public ByteBuffer getBuffer()
  {
    return this.wave.data;
  }

  public int getFormat()
  {
    return this.wave.format;
  }

  public int getSamplerFreq()
  {
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