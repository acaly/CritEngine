package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundEmitter;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.misc.EntityBullet;

public class Shooter
{
  private Entity entity;
  static final int DEFAULT_SHOOT_INTERVAL = 150;
  long lastShootTime;
  public final boolean isPlayer;
  public boolean isShooting;
  int shootInterval;

  public Shooter(EntityPlayer ent)
  {
    this.entity = ent;
    this.shootInterval = 150;
    this.isPlayer = true;
  }

  public Shooter(Entity ent, int shootInterval) {
    this.entity = ent;
    this.shootInterval = shootInterval;
    this.isPlayer = false;
  }

  public void startShooting() {
    shoot();
    if (this.isPlayer)
      this.isShooting = true;
  }

  public void frameUpdate()
  {
    if (this.isShooting)
      shoot();
  }

  public void stopShooting() {
    this.isShooting = false;
  }

  private void shoot() {
    long time = CritEngine.getVirtualTime();
    if (((this.lastShootTime == 0L) || (time - this.lastShootTime > this.shootInterval)) && 
      (ammoSufficient())) {
      this.lastShootTime = time;
      consumeAmmo();
      AttrGeometry geom = this.entity.getGeomProps();
      CESoundEngine.playSound(this.isPlayer ? SceneGame.SND_GUNFIRE : SceneGame.SND_ENEMYFIRE, new SoundEmitter(100, (float)geom.getMinX(), (float)geom.getMinY()));
      this.entity.sceneObj.spawnEntity(new EntityBullet(this.entity.sceneObj, this.entity));
    }
  }

  public boolean ammoSufficient()
  {
    return isPlayer ? ((EntityPlayer)this.entity).getAmmo() > 0 : true;
  }

  public void consumeAmmo() {
    if (this.isPlayer)
      ((EntityPlayer)this.entity).consumeAmmo();
  }
}