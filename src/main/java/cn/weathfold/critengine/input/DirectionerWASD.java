/**
 * 
 */
package cn.weathfold.critengine.input;

import org.lwjgl.input.Keyboard;

/**
 * @author WeAthFolD
 *
 */
public class DirectionerWASD extends Directioner {

	/**
	 * 
	 */
	public DirectionerWASD() {
		// TODO Auto-generated constructor stub
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
