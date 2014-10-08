package cn.weathfold.demo.game.enemy;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.RayTraceResult;
import cn.weathfold.critengine.physics.attribute.AttrCollider;
import cn.weathfold.critengine.physics.attribute.AttrVelocity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.misc.EntityBullet;
import cn.weathfold.demo.game.misc.ObstacleFilter;
import cn.weathfold.demo.game.obstacle.EntityObstacle;
import cn.weathfold.demo.game.player.Shooter;
import org.lwjgl.opengl.GL11;

/**
 * 名字什么的不要在意总之是敌人
 * @author WeAthFolD
 */
public class EntityTraceur extends EntityObstacle {
	LoopAnimation 
		anim, //射击动画
		anim_normal, //正常动画
		anim_dead; //死亡动画
	
	static final double 
		SPEED_HORIZONAL = 100.0D,
		SPEED_VERTICAL = 50.0D,
		MAX_Y = SceneGame.SCENE_HEIGHT;
	
	LoopAnimation currentAnim;
	Shooter shooter;
	
	//state props
	int dirHorizonal, dirVertical;
	boolean isShooting, isHurt;
	double health = 60.0D;
	boolean dead;
	long deadTime,
		lastAttackTime;
	
	//碰撞处理器
	AttrCollider collider = new AttrCollider() {
		@Override
		public boolean onCollided(RayTraceResult res) {
			if ((res.collidedEntity instanceof EntityBullet)) {
				return ((EntityBullet) res.collidedEntity).isPlayer;
			}
			return ObstacleFilter.INSTANCE
					.isEntityApplicable(res.collidedEntity);
		}
	};
	//速度处理器
	AttrVelocity velocity = new AttrVelocity() {
		@Override
		public boolean onVelocityChange(Entity target) {
			return false;
		}

		@Override
		public boolean preVelUpdate() {
			return (!EntityTraceur.this.dead)
					&& (EntityTraceur.this.dirHorizonal > 0);
		}
	};

	public EntityTraceur(Scene scene, double x, double y) {
		super(scene, x, y, 80.0D, 160.0D);
		
		//加载动画
		this.anim = new LoopAnimation(SceneGame.TEX_ENEMY)
				.setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D));
		this.anim_normal = new LoopAnimation(
				new String[] { SceneGame.TEX_ENEMY[0] })
				.setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D)).setFrameInterval(200);
		this.anim_dead = new LoopAnimation(SceneGame.TEX_ENEMY_DEAD)
				.setDrawingQuad(new Rect(0.0D, 0.0D, 158.0D, 167.0D));
		this.currentAnim = this.anim_normal;
		
		//Misc
		this.shooter = new Shooter(this, 1000);
		addAttribute(this.velocity);
		addAttribute(this.collider);
		this.hitDamage = 25;
	}

	@Override
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

	@Override
	public void onFrameUpdate() {
		if (this.dead) {
			processDead();
			return;
		}

		boolean b = playerInRange();
		if ((b) && (!isShooting)) {
			currentAnim = anim;
			currentAnim.setCurrentFrame(0);
		} else if ((!b) && (isShooting)) {
			currentAnim = anim_normal;
			currentAnim.setCurrentFrame(0);
		}
		shooter.isShooting = (isShooting = b);
		shooter.frameUpdate();

		double dx = getScene().thePlayer.getX() - getX(),
				dy = getScene().thePlayer.getY() - getY(),
				tolerance = 20.0D; //位置和玩家位置的允许（不再移动）范围
		dirHorizonal = (dx > 0.0D ? 1 : Math.abs(dx) < tolerance ? 0 : 0);
		dirVertical = (dy > 0.0D ? 1 : Math.abs(dy) < tolerance ? 0 : -1);
		velocity.vel.x = (dirHorizonal * EntityTraceur.SPEED_HORIZONAL);
		velocity.vel.y = (dirVertical * EntityTraceur.SPEED_VERTICAL);

		Rect rect = getGeomProps();
		if ((rect.getMaxY() + rect.getMinY()) / 2.0D > EntityTraceur.MAX_Y)
			rect.setY(EntityTraceur.MAX_Y - rect.getHeight() / 2.0D);
		else if (rect.getMinY() < 0.0D)
			rect.setY(0.0D);
	}

	@Override
	public void attackEntity(Entity ent, int damage) {
		health -= damage;
		lastAttackTime = CritEngine.getVirtualTime();
		if (health <= 0.0D)
			die();
	}

	@Override
	public boolean applyDamageAtSide(RayTraceResult.EnumEdgeSide side) {
		return true;
	}

	private void processDead() {
		if (CritEngine.getVirtualTime() - deadTime > 500L)
			deathFlag = true;
	}

	private void die() {
		dead = true;
		attributes.remove("collider");
		deadTime = CritEngine.getVirtualTime();
		currentAnim = this.anim_dead;
		getScene().score(600);
	}

	private SceneGame getScene() {
		return (SceneGame) this.sceneObj;
	}
	
	private boolean playerInRange() {
		Rect playerPos = getScene().thePlayer.getGeomProps();
		Rect myPos = getGeomProps();
		return (!dead)
				&& (playerPos.getMinX() > myPos.getMinX())
				&& (myPos.intersects(sceneObj.mainCamera.getGeomProps()))
				&& (Rect.ins(playerPos.getMinY(), playerPos.getMaxY(),
						myPos.getMinY() - 30.0D, myPos.getMaxY() + 30.0D));
	}
	
}