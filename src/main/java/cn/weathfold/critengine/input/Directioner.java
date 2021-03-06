/**
 * 
 */
package cn.weathfold.critengine.input;

import org.lwjgl.input.Keyboard;

/**
 * 方向键操作的简单包装，可以借助它方便的读取代表移动方向的两个int值。
 * @author WeAthFolD
 */
public class Directioner extends KeyEventProducer {

	/**
	 * xy轴的移动方向。
	 */
	public int dirHorizonal, dirVertical;

	public Directioner() {
		this.addKeyListening(getKeyUp());
		this.addKeyListening(getKeyDown());
		this.addKeyListening(getKeyLeft());
		this.addKeyListening(getKeyRight());
	}

	/**
	 * 获取上键
	 */
	protected int getKeyUp() {
		return Keyboard.KEY_UP;
	}

	/**
	 * 获取下键
	 */
	protected int getKeyDown() {
		return Keyboard.KEY_DOWN;
	}

	/**
	 * 获取左键
	 */
	protected int getKeyLeft() {
		return Keyboard.KEY_LEFT;
	}

	/**
	 * 获取右键
	 */
	protected int getKeyRight() {
		return Keyboard.KEY_RIGHT;
	}

	@Override
	public void frameUpdate() {
		super.frameUpdate();

		dirHorizonal = -dir(this.isKeyDown(getKeyLeft()))
				+ dir(this.isKeyDown(getKeyRight()));
		dirVertical = -dir(this.isKeyDown(getKeyDown()))
				+ dir(this.isKeyDown(getKeyUp()));
	}

	private int dir(boolean b) {
		return b ? 1 : 0;
	}

	@Override
	public void onKeyDown(int kid) {
	}

	@Override
	public void onKeyUp(int kid) {
	}

	@Override
	public void onKeyFrame(int kid) {
	}

}
