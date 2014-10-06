/**
 * 
 */
package cn.weathfold.critengine.render.animation;

import java.util.Random;

/**
 * 随机循环动画
 * @author WeAthFolD
 *
 */
public class RandomAnimation extends LoopAnimation {
	
	static Random rand = new Random();

	/**
	 * @param textures
	 */
	public RandomAnimation(String[] textures) {
		super(textures);
	}

	/**
	 * @param textures
	 */
	public RandomAnimation(int[] textures) {
		super(textures);
	}
	
	@Override
	protected int nextFrame() {
		return rand.nextInt(texArray.length);
	}

}
