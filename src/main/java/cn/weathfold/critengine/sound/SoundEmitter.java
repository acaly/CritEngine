/**
 * 
 */
package cn.weathfold.critengine.sound;

import org.lwjgl.util.vector.Vector2f;

/**
 * 带位置属性的音源数据。
 * @author WeAthFolD
 *
 */
public class SoundEmitter extends SoundAttributes {
	
	Vector2f pos;
	Vector2f vel;
	
	public SoundEmitter(int life, float x, float y) {
		this(life, x, y, 0, 0);
	}
	
	public SoundEmitter(int life, float x, float y, float vx, float vy) {
		super(life);
		pos = new Vector2f(x, y);
		vel = new Vector2f(vx, vy);
	}
	
	public SoundEmitter(int life) {
		super(life);
		pos = new Vector2f();
		vel = new Vector2f();
	}
}
