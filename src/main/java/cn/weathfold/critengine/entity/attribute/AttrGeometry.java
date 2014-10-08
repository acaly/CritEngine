/**
 * 
 */
package cn.weathfold.critengine.entity.attribute;

import cn.weathfold.critengine.util.Rect;

/**
 * 实体的几何位置属性
 * @author WeAthFolD
 */
public class AttrGeometry extends Rect implements Attribute {

	public AttrGeometry(double x, double y) {
		super(x, y, 0, 0);
	}

	public AttrGeometry(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	@Override
	public String getAttributeID() {
		return "geometry";
	}

}
