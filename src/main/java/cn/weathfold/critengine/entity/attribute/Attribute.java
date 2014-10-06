package cn.weathfold.critengine.entity.attribute;

/**
 * 实体的属性基类。注意属性的id是互斥的，也就是一个实体只能拥有一个某id的attribute。
 * @author WeAthFolD
 */
public interface Attribute {
	
	public String getAttributeID();
	
}
