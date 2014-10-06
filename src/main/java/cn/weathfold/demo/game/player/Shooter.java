/**
 * 
 */
package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.entity.Entity;

/**
 * @author WeAthFolD
 *
 */
public class Shooter {
	
	Entity entity;
	
	long lastShootTime;
	boolean isPlayer;
	boolean isShooting;

	public Shooter(EntityPlayer ent) {
		entity = ent;
		isPlayer = true;
	}
	
	public Shooter(Entity ent, int shootInterval) {
		entity = ent;
		isPlayer = false;
	}
	
	public void startShooting() {
		shoot();
		if(isPlayer) {
			isShooting = true;
		}
	}
	
	public void frameUpdate() {
		if(isShooting)
			shoot();
	}
	
	public void stopShooting() {
		isShooting = false;
	}
	
	private void shoot() {
		
	}
	
	public boolean ammoSufficient() {
		return isPlayer ? ((EntityPlayer)entity).getAmmo() > 0: true;
	}
	
	public void consumeAmmo() {
		if(isPlayer) {
			((EntityPlayer)entity).consumeAmmo();
		}
	}

}
