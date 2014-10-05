/**
 * 
 */
package cn.weathfold.critengine.entity;

/**
 * 实体过滤器接口
 * @author WeAthFolD
 */
public interface IEntityFilter {
	/**
	 * 是否通过这个实体
	 */
	public boolean isEntityApplicable(Entity ent);
}
