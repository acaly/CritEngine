/**
 * 
 */
package cn.weathfold.critengine.util;

/**
 * 描述场景中一块正交矩形区域的类。
 * @author WeAthFolD
 */
public class Rect {
	
	/* 位置坐标 */
	public Vector2d pos;
	
	/* 长宽 */
	public double width, height;
	
	public Rect(double x, double y, double w, double h) {
		pos = new Vector2d(x, y);
		this.width = w;
		this.height = h;
	}
	
	public void addPosition(double x, double y) {
		pos.x += x;
		pos.y += y;
	}
	
	/**
	 * 判断某两个区域是否相交。
	 * @param r2 另外一个Rect
	 */
	public boolean intersects(Rect r2) {
		return ins(getMinX(), getMaxX(), r2.getMinX(), r2.getMaxX())
				&& ins(getMinY(), getMaxY(), r2.getMinY(), r2.getMaxY());
	}
	
	/**
	 * 判断一维线段[a, b], [c, d]是否有交集
	 */
	private boolean ins(double a, double b, double c, double d) {
		return (c <= a && d > a) || (c < b);
	}
	
	public double getMinX() {
		return pos.x;
	}
	
	public double getMinY() {
		return pos.y;
	}
	
	public double getMaxX() {
		return pos.x + width;
	}
	
	public double getMaxY() {
		return pos.y + height;
	}
	
}
