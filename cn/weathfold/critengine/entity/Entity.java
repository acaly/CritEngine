package cn.weathfold.critengine.entity;

import cn.weathfold.critengine.physics.CollideProperty;
import cn.weathfold.critengine.util.Vector2d;

/**
 * Entity��Scene�б�ע�Ტ�ҵ��ã�ִ�е��ǡ�һ����Ϸ���󡱲�ε���Ϊ��
 * @author WeAthFolD
 */
public abstract class Entity {
	
	/* λ�� */
	public Vector2d pos;
	
	/* �Ƿ�Ϊ��ʵ���������㣨�ٶ�/λ�ø��£��������ٶȣ� */
	public boolean enablePhys = false;
	
	/* ��ײ���ԡ� ��ʼ���������ʵ�����ײ */
	public CollideProperty collideProp = null;
	
	public Entity(double x, double y) {
		pos = new Vector2d(x, y);
	}
	
	public Entity setEnablePhysics(boolean b) {
		enablePhys = b;
		return this;
	}
	
	/**
	 * ֡����ʱ��ͬ�����õĺ�����
	 */
	public void onFrameUpdate() {
		
	}

	/**
	 * �ͻ��˻��ƺ��������Ƽ���������(0, 0, 0) -> (width, height, 0)����
	 */
	public void drawEntity() {
		
	}
}
