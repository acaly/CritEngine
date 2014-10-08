/**
 * 
 */
package cn.weathfold.critengine.input;

import org.lwjgl.input.Keyboard;

/**
 * WSAD键的方向侦听
 * @author WeAthFolD
 */
public class DirectionerWASD extends Directioner {

	public DirectionerWASD() {
	}

	@Override
	protected int getKeyUp() {
		return Keyboard.KEY_W;
	}

	@Override
	protected int getKeyDown() {
		return Keyboard.KEY_S;
	}

	@Override
	protected int getKeyLeft() {
		return Keyboard.KEY_A;
	}

	@Override
	protected int getKeyRight() {
		return Keyboard.KEY_D;
	}

}
