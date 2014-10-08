/**
 * 
 */
package cn.weathfold.critengine;

import cn.weathfold.critengine.entity.Entity;

/**
 * “实体处理器”的接口，在引擎内部用来做对实体的接管更新
 * @author WeAthFolD
 */
public interface IEntityProcessor {
	void processEntity(Entity e);
}
