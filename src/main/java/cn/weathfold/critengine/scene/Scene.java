/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.resource.ResourcePool;

/**
 * 场景，是CE一切游戏机制所依附的基础。
 * @author WeAthFolD
 */
public abstract class Scene {
	
	public Camera mainCamera;
	protected static Random RNG = new Random();
	
	/**
	 * 帧更新， 当前Scene被激活时被调用。
	 */
	public void frameUpdate() {
		
	}
	
	/**
	 * 获取在场景中活跃的所有Entity。
	 */
	public abstract Set<Entity> getSceneEntities();
	
	/**
	 * 绘制场景。
	 */
	public abstract void renderScene();
	
	/**
	 * 绘制Scene的固定背景。这层的绘制不受Camera的控制，(0, 0)->(1, 1)的范围将会被映射到整个屏幕上。
	 */
	public void renderBackground() {}
	
	/**
	 * 获取需要渲染的实体列表，已经以渲染顺序排序完毕。
	 */
	public List<Entity> getRenderEntityList() {
		List<Entity> list = new ArrayList<Entity>(getSceneEntities());
		Collections.sort(list, new Comparator<Entity>() {

			@Override
			public int compare(Entity arg0, Entity arg1) {
				int rp0 = arg0.getRenderPriority(),
					rp1 = arg1.getRenderPriority();
				if(rp0 > rp1) {
					return 1;
				} else if(rp0 == rp1) {
					return cmpZLevel(arg0, arg1);
				} else {
					return -1;
				}
			}
			
		});
		return list;
	}
	
	/**
	 * 在资源池中预加载需要的声音和图像文件
	 * @param pool
	 */
	public void preloadResources(ResourcePool pool) {
		
	}
	
	public boolean keepResourcePool() {
		return false;
	}
	
	private int cmpZLevel(Entity a, Entity b) {
		if(a.zlevel == -1) a.zlevel = RNG.nextInt();
		if(b.zlevel == -1) b.zlevel = RNG.nextInt();
		while(a.zlevel == b.zlevel)
			a.zlevel = RNG.nextInt();
		return a.zlevel > b.zlevel ? 1 : -1;
	}
	
}
