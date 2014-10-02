package cn.weathfold.critengine.entity;

import cn.weathfold.critengine.physics.CollideProperty;
import cn.weathfold.critengine.util.Vector2d;

/**
 * Entity在Scene中被注册并且调用，执行的是“一个游戏对象”层次的行为。
 * @author WeAthFolD
 */
public abstract class Entity {
	
	/* 位置 */
	public Vector2d pos;
	
	/* 是否为该实体打开物理计算（速度/位置更新，重力加速度） */
	public boolean enablePhys = false;
	
	/* 碰撞属性。 初始化这个域让实体可碰撞 */
	public CollideProperty collideProp = null;
	
	public Entity(double x, double y) {
		pos = new Vector2d(x, y);
	}
	
	public Entity setEnablePhysics(boolean b) {
		enablePhys = b;
		return this;
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
}
