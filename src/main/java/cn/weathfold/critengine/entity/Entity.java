package cn.weathfold.critengine.entity;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.entity.attribute.Attribute;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

/**
 * Entity在Scene中被注册并且调用，执行的是“一个游戏对象”层次的行为。 自动接管了一个简单的矩形绘制过程。
 * @author WeAthFolD
 */
public class Entity {

	protected Map<String, Attribute> attributes = new HashMap<String, Attribute>(); //实体属性表
	public final Scene sceneObj; // 该实体所属的Scene

	public double zlevel = -1; //用来处理同优先级渲染的问题
	public boolean deathFlag = false; // 死亡flag（大雾），设置为true的话，该实体会在*下一帧*被自动回收。
	
	protected boolean handleDrawing = false; //是否接管绘制？
	protected String textureID; //贴图名
	protected Rect mapping; //uv映射的矩形

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

	public Entity setTexture(String id) {
		setTexture(id, 0, 0, 1, 1);
		return this;
	}

	public Entity setTexture(String id, double u0, double v0, double u1,
			double v1) {
		handleDrawing = true;
		textureID = id;
		mapping = new Rect(u0, v0, u1, v1);
		return this;
	}

	/**
	 * 帧更新时被同步调用的函数。
	 */
	public void onFrameUpdate() {}

	/**
	 * 客户端绘制函数，坐标系为场景坐标系。
	 */
	public void drawEntity() {
		if (handleDrawing) {
			GL11.glPushMatrix(); {
				GL11.glColor4f(1F, 1F, 1F, 1F);
				CERenderEngine.bindTexture(textureID);
				RenderUtils.renderTexturedQuads(this.getGeomProps(),
						mapping.getMinX(), mapping.getMinY(),
						mapping.getMaxX(), mapping.getMaxY());
			} GL11.glPopMatrix();
		}
	}

	/**
	 * 伤害某个实体，为临时接口，以后的版本可能废除
	 */
	//@Deprecated
	public void attackEntity(Entity ent, int damage) {
		// Do nothing by default
	}

	/**
	 * 获取渲染优先级（越高越先渲染）
	 */
	public int getRenderPriority() {
		return 0;
	}

	/**
	 * 获取某个实体属性，如果没有，返回NULL。
	 * @param id 属性id
	 */
	public Attribute getAttribute(String id) {
		return attributes.get(id);
	}

	/**
	 * 获取实体的几何属性
	 */
	public AttrGeometry getGeomProps() {
		return (AttrGeometry) getAttribute("geometry");
	}
	
	/**
	 * 删除某个实体属性
	 * @param str 属性id
	 */
	public Attribute removeAttribute(String str) {
		return attributes.remove(str);
	}

	/**
	 * 添加实体属性
	 * @param attr
	 */
	public void addAttribute(Attribute attr) {
		attributes.put(attr.getAttributeID(), attr);
	}

	/**
	 * 检测某个实体属性是否存在
	 * @param id
	 * @return
	 */
	public boolean hasAttribute(String id) {
		return attributes.containsKey(id);
	}

	public double getX() {
		return getGeomProps().getMinX();
	}

	public double getY() {
		return getGeomProps().getMinY();
	}
	

}
