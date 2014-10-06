/**
 * 
 */
package cn.weathfold.critengine.sound;

/**
 * 一般的音源数据，在PlaySound时使用
 * @author WeAthFolD
 *
 */
public class SoundAttributes {
	public float pitch = 1.0F, gain = 1.0F;
	public int lifeTime;
	
	/**
	 * @param life 声音的生命期（毫秒）
	 */
	public SoundAttributes(int life) {
		lifeTime = life;
	}
	
	public SoundAttributes setGain(float f) {
		gain = f;
		return this;
	}
	
	public SoundAttributes setPitch(float f) {
		pitch = f;
		return this;
	}
}
