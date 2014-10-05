package cn.weathfold.critengine.entity;

import java.util.HashMap;
import java.util.Map;

import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.scene.Scene;

/**
 * Entity在Scene中被注册并且调用，执行的是“一个游戏对象”层次的行为。
 * @author WeAthFolD
 */
public abstract class Entity {
	
	protected Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	public final Scene sceneObj; //该实体所属的Scene
	
	/* 用来处理同优先级渲染的问题 */
	public double zlevel = -1;
	
	public Entity(Scene scene) {
		this(scene, 0, 0, 0, 0);
	}
	
	public Entity(Scene scene, double x, double y) {
		this(scene, x, y, 0, 0);
	}
	
	public Entity(Scene scene, double x, double y, double width, double height) {
		sceneObj = scene;
		this.addAttribute(new AttrGeometry(x, y, width, height));
	}
	
	/**
	 * 帧更新时被同步调用的函数。
	 */
	public void onFrameUpdate() {
		
	}

	/**
	 * 客户端绘制函数。（推荐）绘制在(0, 0, 0) -> (width, height, 0)区域。
	 */
	public void drawEntity() {
		
	}
	
	public int getRenderPriority() {
		return 1;
	}
	
	public Attribute getAttribute(String id) {
		return attributes.get(id);
	}
	
	public AttrGeometry getGeomProps() {
		return (AttrGeometry) getAttribute("geometry");
	}
	
	protected void addAttribute(Attribute attr) {
		attributes.put(attr.getAttributeID(), attr);
	}
}
