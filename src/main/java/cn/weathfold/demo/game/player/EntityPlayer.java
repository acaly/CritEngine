package cn.weathfold.demo.game.player;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.GameTimer;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.input.Directioner;
import cn.weathfold.critengine.input.DirectionerWASD;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.util.Vector2d;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.attributes.PlayerCollider;
import cn.weathfold.demo.game.player.attributes.PlayerVel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class EntityPlayer extends Entity {
	public static final double SPEED_NORMAL = 250.0D;
	public static final double SPEED_CATCHUP = 340.0D;
	public static final double DEFAULT_SCREEN_OFFSET = 550.0D;
	public static final double JUDGEBOX_SIZE = 68.0D;
	public static final double SHADE_SIZE = 100.0D;
	public static final double HEIGHT = 180.0D;
	public static final double JUMPING_HEIGHT = 130.0D;
	public static final double UPDOWN_SPEED = 280.0D;
	public static final double GRAVITY = 0.002D;
	public static final long JUMP_CHARGE_TIME = 300L;
	private Directioner directioner = new DirectionerWASD();
	private KeyEventProducer controller = new KeyEventProducer() {
		long jumpPressTime;
		{
			this.addKeyListening(Keyboard.KEY_SPACE);
			this.addKeyListening(MOUSE0);
		}

		public void onKeyDown(int key) {
			if (key == Keyboard.KEY_SPACE) {
				this.jumpPressTime = CritEngine.getVirtualTime();
			} else if (key == MOUSE0) {
				EntityPlayer.this.shooter.isShooting = true;
				EntityPlayer.this.shooter.frameUpdate();
			}
		}

		public void onKeyFrame(int kid) {
			if (kid == Keyboard.KEY_SPACE) {
				long dt = CritEngine.getVirtualTime() - this.jumpPressTime;
				if (dt >= JUMP_CHARGE_TIME) {
					EntityPlayer.this.attemptJump(checkHeight());
					this.jumpPressTime = 0L;
				}
			} else if (kid == MOUSE0) {
				EntityPlayer.this.shooter.frameUpdate();
			}
		}

		public void onKeyUp(int kid) {
			if (kid == Keyboard.KEY_SPACE) {
				if (this.jumpPressTime == 0L)
					return;
				EntityPlayer.this.attemptJump(checkHeight());
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
	double health;
	boolean isCollided;
	long jumpTime;
	long lastAttackedTime;
	double halfJumpLen;
	double jumpHeight;
	double currentHeight;
	public int ammo = 20;

	PlayerVel velProcess = new PlayerVel(this);
	PlayerCollider collider = new PlayerCollider(this);
	Shooter shooter = new Shooter(this);

	public EntityPlayer(SceneGame scene) {
		super(scene, -256.0D, 100.0D, 68.0D, 68.0D);

		addAttribute(this.velProcess);
		addAttribute(this.collider);
		setTexture("edge");
		this.health = 100.0D;
	}

	public void resetPosition() {
		AttrGeometry geom = getGeomProps();
		this.ammo = 20;
		this.health = 100.0D;
		this.jumpTime = 0L;
		geom.pos.x = -256.0D;
		geom.pos.y = 100.0D;
	}

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

		double yChange = this.directioner.dirVertical * 280.0D
				* CritEngine.getTimer().getElapsedTime() / 1000.0D;
		geom.pos.y += yChange;
		if (geom.pos.y > SceneGame.SCENE_HEIGHT - 68.0D)
			geom.pos.y = (SceneGame.SCENE_HEIGHT - 68.0D);
		else if (geom.pos.y < 0.0D) {
			geom.pos.y = 0.0D;
		}

		jumpUpdate();
	}

	protected void jumpUpdate() {
		if (this.jumpTime != 0L) {
			long dt = CritEngine.getVirtualTime() - this.jumpTime;
			if (dt > 2.0D * this.halfJumpLen) {
				this.jumpTime = 0L;
				return;
			}
			double tmp = dt - this.halfJumpLen;
			this.currentHeight = (-tmp * tmp * 0.002D / 2.0D + this.jumpHeight);
		} else {
			this.currentHeight = 0.0D;
		}
	}

	protected void attemptJump(double height) {
		if (this.jumpTime != 0L)
			return;
		this.jumpTime = CritEngine.getVirtualTime();
		this.jumpHeight = height;
		this.halfJumpLen = Math.sqrt(2.0D * height / 0.002D);
		this.currentHeight = 0.0D;
	}

	public void drawEntity() {
		double scaleShadow = 0.5D * (1.0D - this.currentHeight / JUMPING_HEIGHT) + 0.5D;
		Rect pos = getGeomProps();
		SceneGame scene = getScene();
		GL11.glPushMatrix();
		GL11.glTranslated(pos.getMinX() - 85.0D, pos.getMinY(), 0.0D);

		GL11.glPushMatrix();
		GL11.glTranslated(68.0D, 6.0D, 0.0D);
		GL11.glTranslated(50.0D, 50.0D, 0.0D);
		GL11.glScaled(scaleShadow, scaleShadow, 1.0D);
		CERenderEngine.bindTexture("pshadow");
		GL11.glTranslated(-50.0D, -50.0D, 0.0D);
		RenderUtils.renderTexturedQuads(0.0D, 0.0D, 100.0D, 100.0D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		if (isAttacked()) {
			GL11.glColor4f(1.0F, 0.2F, 0.2F, 1.0F);
		}
		GL11.glTranslated(0.0D, 22.0D + this.currentHeight, 0.0D);
		if ((this.shooter.isShooting) && (getAmmo() > 0))
			scene.animShooting.draw();
		else {
			scene.animNormal.draw();
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		super.drawEntity();
	}

	private SceneGame getScene() {
		return (SceneGame) this.sceneObj;
	}

	public void attackEntity(Entity ent, int damage) {
		// System.out.println(ent + " " + damage);
		if (this.jumpTime != 0L)
			return;
		this.health -= damage;
		this.lastAttackedTime = CritEngine.getVirtualTime();
		if (this.health <= 0.0D)
			getScene().gameOver();
	}

	public double getHealth() {
		return this.health;
	}

	public boolean isAttacked() {
		return CritEngine.getVirtualTime() - this.lastAttackedTime < 400L;
	}

	public void heal(int i) {
		this.health += i;
		if (this.health > 100.0D)
			this.health = 100.0D;
	}

	public int getAmmo() {
		return this.ammo;
	}

	public void consumeAmmo() {
		if (--this.ammo < 0)
			this.ammo = 0;
	}

	public int getRenderPriority() {
		return 3;
	}
}