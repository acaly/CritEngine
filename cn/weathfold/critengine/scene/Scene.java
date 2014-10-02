/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.Set;

import cn.weathfold.critengine.entity.Entity;

/**
 * ��������CEһ����Ϸ�����������Ļ�����
 * @author WeAthFolD
 */
public abstract class Scene {
	
	/**
	 * ��ȡ�ڳ����л�Ծ������Entity��
	 */
	public abstract Set<Entity> getSceneEntities();
	
	/**
	 * ���Ƴ�����ͨ���Ǳ����Ⱥ�����ݡ��Ƽ���Χ��(0, 0)->(1, 1)����
	 */
	public abstract void renderScene();
	
}
