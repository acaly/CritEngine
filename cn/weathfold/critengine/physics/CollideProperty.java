/**
 * 
 */
package cn.weathfold.critengine.physics;

import cn.weathfold.critengine.util.Rect;

/**
 * @author WeAthFolD
 *
 */
public class CollideProperty {
	
	Rect boundingBox;
	
	public CollideProperty(double width, double height) {
		boundingBox = new Rect(0, 0, width, height);
	}
	
}
