/**
 * 
 */
package cn.weathfold.critengine.physics;

import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.entity.attribute.Attribute;

/**
 * @author WeAthFolD
 *
 */
public class CollideProperty implements Attribute {
	
	Rect boundingBox;
	
	public CollideProperty(double width, double height) {
		boundingBox = new Rect(0, 0, width, height);
	}

	@Override
	public String getAtttributeID() {
		return "collision";
	}
	
}
