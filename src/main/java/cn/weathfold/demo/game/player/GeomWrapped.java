/**
 * 
 */
package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.RayTraceResult.EnumEdgeSide;

/**
 * 跳跃支持~用一些微妙的小算法实现伪高度判断
 * @author WeAthFolD
 */
public class GeomWrapped extends AttrGeometry {
	
	EntityPlayer player;
	AttrGeometry realGeom;
	double maxHeight; //最大高度
	double curHeight = 0; //当前高度
	double curVelocity; //当前y速度
	boolean state = false; //是否被激活

	/**
	 * @param x
	 * @param y
	 */
	public GeomWrapped(EntityPlayer player) {
		super(0, 0);
		realGeom = player.getGeomProps();
		this.player = player;
		this.pos.x = realGeom.getMinX();
		this.pos.y = realGeom.getMinY();
		this.width = realGeom.getWidth();
		this.height = realGeom.getHeight();
	}
	
	public void enter(double height) {
		if(state)
			return;
		this.pos.x = realGeom.getMinX();
		maxHeight = height;
		player.removeAttribute("geometry");
		player.addAttribute(this);
		curVelocity = Math.sqrt(2 * maxHeight * EntityPlayer.GRAVITY);
		state = true;
	}
	
	public boolean onCollided(RayTraceResult res) {
		if(res.edge == EnumEdgeSide.TOP) {
			curVelocity = 0;
		}
		
		return res.collidedEntity.getGeomProps().intersects(realGeom);
	}
	
	public void quit() {
		player.removeAttribute("geometry");
		player.addAttribute(realGeom);
		state = false;
	}
	
	public void frameUpdate() {
		if(!state)
			return;
		
		curHeight += curVelocity * CritEngine.getTimer().getElapsedTime() / 1000D;
		curVelocity -= CritEngine.getTimer().getElapsedTime() * EntityPlayer.GRAVITY / 1000D;
		
		if(curHeight <= 0 && curVelocity <= 0) {
			quit();
		}
	}
	
	public double getJumpingHeight() {
		return state ? curHeight : 0.0D;
	}
	
	@Override
	public void setX(double d) {
		pos.x = d;
		realGeom.setX(d);
	}

	@Override
	public double getMinY() {
		return realGeom.getMinY() + getJumpingHeight();
	}

}
