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
	
	public Rect(Rect r2) {
		this(r2.pos.x, r2.pos.y, r2.width, r2.height);
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
		//System.out.println(a + " " + b + " " + c + " " + d + ((c <= a && d > a) || (c < b)));
		return d <= a ? false : (c < b);
	}
	
	public void expand(Rect r2) {
		pos.x = Math.min(r2.getMinX(), pos.x);
		pos.y = Math.min(r2.getMinY(), pos.y);
		double 
			maxX = Math.max(getMaxX(), r2.getMaxX()),
			maxY = Math.max(getMaxY(), r2.getMaxY());
		width = maxX - pos.x;
		height = maxY - pos.y;
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
	
	@Override
	public String toString() {
		return "[RECT " + pos.x + " " + pos.y + " " + getMaxX() + " " + getMaxY() + "]";
	}
	
}
