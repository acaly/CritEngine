package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.input.Directioner;
import cn.weathfold.critengine.input.DirectionerWASD;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.SceneGame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * 玩家实体类~基本完整的演示了CE中实体所能实现的功能
 * @author WeAthFolD
 */
public class EntityPlayer extends Entity {
	
	//Constants
	public static final double 
		SPEED_NORMAL = 250.0D,
		SPEED_CATCHUP = 340.0D,
		DEFAULT_SCREEN_OFFSET = 550.0D,
		JUDGEBOX_SIZE = 68.0D,
		SHADE_SIZE = 100.0D,
		HEIGHT = 180.0D,
		JUMPING_HEIGHT = 200.0D,
		UPDOWN_SPEED = 280.0D, //上下移动的速度
		GRAVITY = 2000D; //起跳时重力加速度
	
	public static final long JUMP_CHARGE_TIME = 200L;
	
	//侦听ESC（暂停界面） 鼠标左键（射击）
	private KeyEventProducer controller = new KeyEventProducer() {
		
		long jumpPressTime;
		
		{
			this.addKeyListening(Keyboard.KEY_SPACE);
			this.addKeyListening(MOUSE0);
		}

		@Override
		public void onKeyDown(int key) {
			if (key == Keyboard.KEY_SPACE) {
				this.jumpPressTime = CritEngine.getVirtualTime();
			} else if (key == MOUSE0) {
				EntityPlayer.this.shooter.isShooting = true;
				EntityPlayer.this.shooter.frameUpdate();
			}
		}

		@Override
		public void onKeyFrame(int kid) {
			if (kid == Keyboard.KEY_SPACE) {
				long dt = CritEngine.getVirtualTime() - this.jumpPressTime;
				if (dt >= JUMP_CHARGE_TIME) {
					jumpGeom.enter(checkHeight());
					this.jumpPressTime = 0L;
				}
			} else if (kid == MOUSE0) {
				EntityPlayer.this.shooter.frameUpdate();
			}
		}

		@Override
		public void onKeyUp(int kid) {
			if (kid == Keyboard.KEY_SPACE) {
				if (this.jumpPressTime == 0L)
					return;
				jumpGeom.enter(checkHeight());
			} else if (kid == MOUSE0) {
				EntityPlayer.this.shooter.isShooting = false;
			}
		}

		private double checkHeight() {
			long dt = CritEngine.getVirtualTime() - this.jumpPressTime;
			if (dt > JUMP_CHARGE_TIME)
				dt = JUMP_CHARGE_TIME;
			return (0.3D + 0.7D * dt / 500.0D) * JUMPING_HEIGHT;
		}
	};
	
	public int ammo = 20;
	
	double health;
	boolean isCollided;
	long jumpTime;
	long lastAttackedTime;

	PlayerVel velProcess = new PlayerVel(this);
	PlayerCollider collider = new PlayerCollider(this);
	Shooter shooter = new Shooter(this);
	GeomWrapped jumpGeom;
	Directioner directioner = new DirectionerWASD();

	public EntityPlayer(SceneGame scene) {
		super(scene, -256.0D, 100.0D, JUDGEBOX_SIZE, JUDGEBOX_SIZE);

		addAttribute(this.velProcess);
		addAttribute(this.collider);
		setTexture("edge");
		this.health = 100.0D;
		jumpGeom = new GeomWrapped(this);
	}

	/**
	 * 重置玩家位置
	 */
	public void resetPosition() {
		AttrGeometry geom = getGeomProps();
		this.ammo = 20;
		this.health = 100.0D;
		this.jumpTime = 0L;
		geom.setPos(-256, 100);
	}

	@Override
	public void onFrameUpdate() {
		if (getScene().gameOver) {
			return;
		}
		this.velProcess.frameUpdate();
		this.directioner.frameUpdate();
		this.controller.frameUpdate();

		if (!getGeomProps().intersects(getScene().mainCamera.getGeomProps())) {
			getScene().gameOver();
		}

		AttrGeometry geom = getGeomProps();
		if (geom != jumpGeom) {
			double yChange = this.directioner.dirVertical * 280.0D * 
					CritEngine.getTimer().getElapsedTime() / 1000.0D;
			geom.setY(geom.getMinY() + yChange);
			if (geom.getMinY() > SceneGame.SCENE_HEIGHT - 68.0D)
				geom.setY(SceneGame.SCENE_HEIGHT - 68.0D);
			else if (geom.getMinY() < 0.0D) {
				geom.setY(0.0D);
			} 
		} else {
			jumpGeom.frameUpdate();
		}
	}

	@Override
	public void drawEntity() {
		double scaleShadow = 0.5D * (1.0D - jumpGeom.getJumpingHeight() / JUMPING_HEIGHT) + 0.5D;
		double jumpHeight = jumpGeom.getJumpingHeight();
		Rect pos = getGeomProps();
		SceneGame scene = getScene();
		
		GL11.glPushMatrix(); {
			
			GL11.glTranslated(pos.getMinX() - 85.0D, pos.getMinY(), 0.0D);
			GL11.glTranslated(0, -jumpHeight, 0);
			
			//绘制阴影
			GL11.glPushMatrix(); {
				GL11.glTranslated(68.0D, 6.0D, 0.0D);
				GL11.glTranslated(50.0D, 50.0D, 0.0D);
				GL11.glScaled(scaleShadow, scaleShadow, 1.0D);
				CERenderEngine.bindTexture("pshadow");
				GL11.glTranslated(-50.0D, -50.0D, 0.0D);
				RenderUtils.renderTexturedQuads(0.0D, 0.0D, 100.0D, 100.0D);
			} GL11.glPopMatrix();

			//绘制玩家本体
			GL11.glPushMatrix(); {
				if (isAttacked()) {
					GL11.glColor4f(1.0F, 0.2F, 0.2F, 1.0F);
				}
				GL11.glTranslated(0.0D, 22.0D + jumpHeight, 0.0D);
				if ((this.shooter.isShooting) && (getAmmo() > 0))
					scene.animShooting.draw();
				else {
					scene.animNormal.draw();
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			} GL11.glPopMatrix();
		} GL11.glPopMatrix();
		
	}

	@Override
	public void attackEntity(Entity ent, int damage) {
		if (this.jumpTime != 0L)
			return;
		this.health -= damage;
		this.lastAttackedTime = CritEngine.getVirtualTime();
		if (this.health <= 0.0D)
			getScene().gameOver();
	}
	
	public boolean isAttacked() {
		return CritEngine.getVirtualTime() - this.lastAttackedTime < 400L;
	}

	/**
	 * 获取当前血量
	 * @return
	 */
	public double getHealth() {
		return this.health;
	}

	/**
	 * 治愈玩家（加血）
	 * @param i
	 */
	public void heal(int i) {
		this.health += i;
		if (this.health > 100.0D)
			this.health = 100.0D;
	}

	/**
	 * 获取残弹量
	 * @return
	 */
	public int getAmmo() {
		return this.ammo;
	}

	/**
	 * 消耗玩家弹药
	 */
	public void consumeAmmo() {
		if (--this.ammo < 0)
			this.ammo = 0;
	}

	@Override
	public int getRenderPriority() {
		return 3;
	}
	
	private SceneGame getScene() {
		return (SceneGame) this.sceneObj;
	}
}