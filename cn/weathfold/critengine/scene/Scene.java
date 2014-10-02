/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.Set;

import cn.weathfold.critengine.entity.Entity;

/**
 * 场景，是CE一切游戏机制所依附的基础。
 * @author WeAthFolD
 */
public abstract class Scene {
	
	/**
	 * 获取在场景中活跃的所有Entity。
	 */
	public abstract Set<Entity> getSceneEntities();
	
	/**
	 * 绘制场景，通常是背景等后层内容。推荐范围：(0, 0)->(1, 1)填满
	 */
	public abstract void renderScene();
	
}
