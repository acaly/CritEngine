/**
 * 
 */
package cn.weathfold.critengine.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * 键盘事件侦听的简单包装。继承它来定义具体的侦听行为
 * @author WeAthFolD
 */
public abstract class KeyEventProducer {
	
	private Map<Integer, Boolean> listenedKeys = new HashMap<Integer, Boolean>();
	
	public static final int 
		MOUSE0 = -100,
		MOUSE1 = -99,
		MOUSE2 = -98;
	
	private boolean loaded = false;
	
	public void addKeyListening(int keyid) {
		listenedKeys.put(keyid, false);
	}
	
	public void frameUpdate() {
		if(!loaded) {
			loaded = true;
			for(Map.Entry<Integer, Boolean> ent : listenedKeys.entrySet()) {
				ent.setValue(isKeyDown(ent.getKey()));
			}
		}
		
		for(Map.Entry<Integer, Boolean> ent : listenedKeys.entrySet()) {
			boolean state = isKeyDown(ent.getKey());
			if(state && !ent.getValue()) {
				onKeyDown(ent.getKey());
			} else if(!state && ent.getValue()) {
				onKeyUp(ent.getKey());
			} else if(state) {
				onKeyFrame(ent.getKey());
			}
			ent.setValue(state);
		}
	}
	
	protected boolean isKeyDown(int kid) {
		if(kid > 0)
			return Keyboard.isKeyDown(kid);
		else return Mouse.isButtonDown(kid + 100);
	}
	
	public abstract void onKeyDown(int kid);
	
	public abstract void onKeyFrame(int kid);
	
	public abstract void onKeyUp(int kid);
}
