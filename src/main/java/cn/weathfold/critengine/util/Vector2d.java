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

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2d normalize() {
		double z = Math.sqrt(x * x + y * y);
		return new Vector2d(x / z, y / z);
	}
	
	public void addVector(double x, double y) {
		this.x += x;
		this.y += y;
	}

}
