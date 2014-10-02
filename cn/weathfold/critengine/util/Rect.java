/**
 * 
 */
package cn.weathfold.critengine.util;

/**
 * ����������һ����������������ࡣ
 * @author WeAthFolD
 */
public class Rect {
	
	/* λ������ */
	public Vector2d pos;
	
	/* ���� */
	public double width, height;
	
	public Rect(double x, double y, double w, double h) {
		pos = new Vector2d(x, y);
		this.width = w;
		this.height = h;
	}
	
	/**
	 * �ж�ĳ���������Ƿ��ཻ��
	 * @param r2 ����һ��Rect
	 */
	public boolean intersects(Rect r2) {
		return ins(getMinX(), getMaxX(), r2.getMinX(), r2.getMaxX())
				&& ins(getMinY(), getMaxY(), r2.getMinY(), r2.getMaxY());
	}
	
	/**
	 * �ж�һά�߶�[a, b], [c, d]�Ƿ��н���
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
