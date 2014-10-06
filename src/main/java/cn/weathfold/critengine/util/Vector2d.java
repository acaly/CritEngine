/**
 * 
 */
package cn.weathfold.critengine.util;


/**
 * @author WeAthFolD
 *
 */
public class Vector2d {
	
	public double x, y;
	
	public Vector2d() {
		this(0, 0);
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/* 获取一个副本 */
	public Vector2d copy() {
		return new Vector2d(x, y);
	}
	
	public double getDistSquared() {
		return x * x + y * y;
	}
	
	public double getMod() {
		return Math.sqrt(getDistSquared());
	}
	
	public double distanceTo(Vector2d another) {
		return distanceTo(another.x, another.y);
	}
	
	public double distanceTo(double x, double y) {
		double dx = this.x - x,
				dy = this.y - y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public void normalize() {
		double z = Math.sqrt(x * x + y * y);
		x /= z;
		y /= z;
	}
	
	public void addVector(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Vector2d))
			return false;
		Vector2d v2 = (Vector2d) obj;
		return v2.x == x && v2.y == y;
	}

}
