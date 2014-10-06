/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.util.Rect;

/**
 * 场景，是CE一切游戏机制所依附的基础。
 * @author WeAthFolD
 */
public abstract class Scene {
	
	public Camera mainCamera;
	protected static Random RNG = new Random();
	
	protected Set<Entity> elements = new HashSet<Entity>();

	public Set<Entity> getSceneEntities() {
		return elements;
	}
	
	/**
	 * 帧更新， 当前Scene被激活时被调用。
	 */
	public void frameUpdate() {
		
	}
	
	/**
	 * 绘制Scene的固定背景，(0, 0)->(SCREEN WIDTH, SCREEN HEIGHT)的范围将会被映射到整个屏幕上。
	 */
	public void renderBackground() {}
	
	/**
	 * 绘制Scene的前景，(0, 0)->(SCREEN WIDTH, SCREEN HEIGHT)的范围将会被映射到整个屏幕上。
	 */
	public void renderForeground() {
		if(mainCamera != null)
			this.mainCamera.draw();
	}
	
	/**
	 * 获取在矩形范围内（或穿过）的所有实体。
	 * @param rt
	 * @return
	 */
	public Set<Entity> getEntitiesWithin(Rect rt) {
		return getEntitiesWithin(rt, null);
	}
	
	public Set<Entity> getEntitiesWithin(Rect rt, IEntityFilter filter, Entity... exceptions) {
		Set<Entity> res = new HashSet<Entity>();
		
		for(Entity e : getSceneEntities()) {
			if(rt.intersects(e.getGeomProps())) {
				if(filter != null && !filter.isEntityApplicable(e))
					continue;
				boolean flag = false;
				for(Entity exp : exceptions) {
					if(exp == e) {
						flag = true;
						break;
					}
				}
				if(flag) continue;
				res.add(e);
			}
		}
		
		return res;
	}
	
	public void spawnEntity(Entity e) {
		if(e.sceneObj != this) {
			throw new RuntimeException("Attempting to add an entity from another scene : " + e);
		}
		elements.add(e);
	}
	
	/**
	 * 获取需要渲染的实体列表，已经以渲染顺序排序完毕。
	 */
	public List<Entity> getRenderEntityList() {
		Set<Entity> set = getSceneEntities();
		if(set == null)
			return new ArrayList<Entity>();
		
		List<Entity> list = new ArrayList<Entity>(set);
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
	
	/**
	 * 在场景被切换的时候调用，执行收尾工作。
	 */
	public void onDisposed() {}
	
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
