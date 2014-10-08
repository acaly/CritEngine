package cn.weathfold.demo.game.enemy;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.RayTraceResult.EnumEdgeSide;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.util.Vector2d;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.misc.EntityBullet;
import cn.weathfold.demo.game.misc.ObstacleFilter;
import cn.weathfold.demo.game.obstacle.EntityObstacle;
import cn.weathfold.demo.game.player.EntityPlayer;
import cn.weathfold.demo.game.player.Shooter;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class EntityTraceur extends EntityObstacle
{
  LoopAnimation anim;
  LoopAnimation anim_normal;
  LoopAnimation anim_dead;
  double SPEED_HORIZONAL = 100.0D;
  double SPEED_VERTICAL = 50.0D;
  double MAX_Y = SceneGame.SCENE_HEIGHT;
  boolean isShooting;
  boolean isHurt;
  LoopAnimation currentAnim;
  Shooter shooter;
  int dirHorizonal;
  int dirVertical;
  double health = 60.0D;
  boolean dead;
  long deadTime;
  AttrCollider collider = new AttrCollider()
  {
    public boolean onCollided(RayTraceResult res) {
      if ((res.collidedEntity instanceof EntityBullet)) {
        return ((EntityBullet)res.collidedEntity).isPlayer;
      }
      return ObstacleFilter.INSTANCE.isEntityApplicable(res.collidedEntity);
    }
  };

  AttrVelocity velocity = new AttrVelocity()
  {
    public boolean onVelocityChange(Entity target)
    {
      return false;
    }

    public boolean preVelUpdate() {
      return (!EntityTraceur.this.dead) && (EntityTraceur.this.dirHorizonal > 0);
    }
  };

  public EntityTraceur(Scene scene, double x, double y)
  {
    super(scene, x, y, 80.0D, 160.0D);
    this.anim = new LoopAnimation(SceneGame.TEX_ENEMY).setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D));
    this.anim_normal = new LoopAnimation(new String[] { 
      SceneGame.TEX_ENEMY[0] }).setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D));
    this.anim_dead = new LoopAnimation(SceneGame.TEX_ENEMY_DEAD).setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D));
    this.currentAnim = this.anim_normal;
    this.shooter = new Shooter(this, 600);
    addAttribute(this.velocity);
    addAttribute(this.collider);
  }

  public void drawEntity() {
    GL11.glPushMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    Rect pos = getGeomProps();
    CERenderEngine.bindTexture("edge");
    RenderUtils.renderTexturedQuads(pos);

    GL11.glTranslated(pos.getMinX(), pos.getMinY(), 0.0D);
    this.currentAnim.draw();
    GL11.glPopMatrix();
  }

  public void onFrameUpdate()
  {
    if (this.dead) {
      processDead();
      return;
    }

    boolean b = playerInRange();
    if ((b) && (!this.isShooting)) {
      this.currentAnim = this.anim;
      this.currentAnim.setCurrentFrame(0);
    } else if ((!b) && (this.isShooting)) {
      this.currentAnim = this.anim_normal;
      this.currentAnim.setCurrentFrame(0);
    }
    this.shooter.isShooting = (this.isShooting = b);
    this.shooter.frameUpdate();

    double dx = getScene().thePlayer.getX() - getX();
    double dy = getScene().thePlayer.getY() - getY();
    double tolerance = 20.0D;
    this.dirHorizonal = (dx > 0.0D ? 1 : Math.abs(dx) < 20.0D ? 0 : 0);
    this.dirVertical = (dy > 0.0D ? 1 : Math.abs(dy) < 20.0D ? 0 : -1);
    this.velocity.vel.x = (this.dirHorizonal * this.SPEED_HORIZONAL);
    this.velocity.vel.y = (this.dirVertical * this.SPEED_VERTICAL);

    Rect rect = getGeomProps();
    if ((rect.getMaxY() + rect.getMinY()) / 2.0D > this.MAX_Y)
      rect.pos.y = (this.MAX_Y - rect.height / 2.0D);
    else if (rect.pos.y < 0.0D)
      rect.pos.y = 0.0D;
  }

  private boolean playerInRange()
  {
    Rect playerPos = getScene().thePlayer.getGeomProps();
    Rect myPos = getGeomProps();
    return (!this.dead) && (playerPos.getMinX() > myPos.getMinX()) && 
    		(myPos.intersects(this.sceneObj.mainCamera.getGeomProps())) && (Rect.ins(playerPos.getMinY(), playerPos.getMaxY(), myPos.getMinY() - 30.0D, myPos.getMaxY() + 30.0D));
  }

  public void attackEntity(Entity ent, int damage)
  {
    this.health -= damage;
    if (this.health <= 0.0D)
      die();
  }

  public boolean applyDamageAtSide(RayTraceResult.EnumEdgeSide side)
  {
    return true;
  }

  private void processDead() {
    if (CritEngine.getVirtualTime() - this.deadTime > 500L)
      this.deathFlag = true;
  }

  private void die()
  {
    this.dead = true;
    this.attributes.remove("collider");
    this.deadTime = CritEngine.getVirtualTime();
    this.currentAnim = this.anim_dead;
  }

  private SceneGame getScene() {
    return (SceneGame)this.sceneObj;
  }
}