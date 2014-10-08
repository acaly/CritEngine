/**
 * 
 */
package cn.weathfold.critengine.util;

/**
 * 描述场景中一块正交矩形区域的类，在实体位置、碰撞箱等相当多的位置用到
 * @author WeAthFolD
 */
public class Rect {

	protected Vector2d pos; //位置坐标

	protected double width, height; //长宽

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
	public static boolean ins(double a, double b, double c, double d) {
		return d <= a ? false : (c < b);
	}

	/**
	 * 获取两个区域的合并（扩张）
	 */
	public void expand(Rect r2) {
		pos.x = Math.min(r2.getMinX(), pos.x);
		pos.y = Math.min(r2.getMinY(), pos.y);
		double maxX = Math.max(getMaxX(), r2.getMaxX()), maxY = Math.max(
				getMaxY(), r2.getMaxY());
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
		return getMinX() + width;
	}

	public double getMaxY() {
		return getMinY() + height;
	}
	
	/**
	 * 设置原点x
	 * @param x
	 */
	public void setX(double x) {
		pos.x = x;
	}
	
	/**
	 * 设置原点y
	 * @param y
	 */
	public void setY(double y) {
		pos.y = y;
	}
	
	/**
	 * 获取高
	 * @return
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * 获取宽
	 * @return
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * 设置矩形原点位置
	 * @param x
	 * @param y
	 */
	public void setPos(double x, double y) {
		pos.x = x;
		pos.y = y;
	}
	
	/**
	 * 移动矩形的原点位置
	 * @param x 移动的x
	 * @param y 移动的y
	 */
	public void move(double x, double y) {
		pos.x += x;
		pos.y += y;
	}
	
	/**
	 * 设置宽
	 * @param w
	 */
	public void setWidth(double w) {
		width = w;
	}
	
	/**
	 * 设置高
	 * @param h
	 */
	public void setHeight(double h) {
		height = h;
	}
	
	/**
	 * 获取实体位置的Vector2d
	 * @return
	 */
	public Vector2d getPos() {
		return pos;
	}

	@Override
	public String toString() {
		return "[RECT " + pos.x + " " + pos.y + " " + getMaxX() + " " + getMaxY() + "]";
	}

}
