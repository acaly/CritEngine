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
	
	protected int getKeyUp() {
		return Keyboard.KEY_W;
	}
	
	protected int getKeyDown() {
		return Keyboard.KEY_S;
	}
	
	protected int getKeyLeft() {
		return Keyboard.KEY_A;
	}
	
	protected int getKeyRight() {
		return Keyboard.KEY_D;
	}

}
