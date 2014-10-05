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
	
	public double getDistance() {
		return Math.sqrt(getDistSquared());
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

}
