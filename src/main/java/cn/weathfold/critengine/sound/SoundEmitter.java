/**
 * 
 */
package cn.weathfold.critengine.sound;

import org.lwjgl.util.vector.Vector2f;

/**
 * @author WeAthFolD
 *
 */
public class SoundEmitter extends SoundAttributes {
	
	Vector2f pos;
	Vector2f vel;
	
	public SoundEmitter(float x, float y) {
		this(x, y, 0, 0);
	}
	
	public SoundEmitter(float x, float y, float vx, float vy) {
		pos = new Vector2f(x, y);
		vel = new Vector2f(vx, vy);
	}
	
	public SoundEmitter() {
		pos = new Vector2f();
		vel = new Vector2f();
	}
}
