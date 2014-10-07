/**
 * 
 */
package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.demo.game.misc.EntityBullet;

/**
 * @author WeAthFolD
 *
 */
public class Shooter {
	
	Entity entity;
	static final int DEFAULT_SHOOT_INTERVAL = 300;
	
	long lastShootTime;
	public final boolean isPlayer;
	public boolean isShooting;
	int shootInterval;

	public Shooter(EntityPlayer ent) {
		entity = ent;
		shootInterval = DEFAULT_SHOOT_INTERVAL;
		isPlayer = true;
	}
	
	public Shooter(Entity ent, int shootInterval) {
		entity = ent;
		this.shootInterval = shootInterval;
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
		long time = CritEngine.getVirtualTime();
		if(lastShootTime == 0 || time - lastShootTime > shootInterval) {
			System.out.println("Attempting to shoot once");
			if(ammoSufficient()) {
				lastShootTime = time;
				consumeAmmo();
				entity.sceneObj.spawnEntity(new EntityBullet(entity.sceneObj, entity));
			}
		}
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
