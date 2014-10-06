/**
 * 
 */
package cn.weathfold.demo.game.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.input.Directioner;
import cn.weathfold.critengine.input.DirectionerWASD;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.render.animation.RandomAnimation;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.attributes.PlayerCollider;
import cn.weathfold.demo.game.player.attributes.PlayerVel;

/**
 * @author WeAthFolD
 *
 */
public class EntityPlayer extends Entity {
	
	public static final double
		SPEED_NORMAL = 180,
		SPEED_CATCHUP = 300,
		DEFAULT_SCREEN_OFFSET = 550;
	public static final double
		JUDGEBOX_SIZE = 68,
		SHADE_SIZE = 100,
		HEIGHT = 180,
		JUMPING_HEIGHT = 180,
		UPDOWN_SPEED = 280,
		GRAVITY = 0.002;
	public static final long //最长蓄力时间
		JUMP_CHARGE_TIME = 800;
	
	private Directioner directioner = new DirectionerWASD();
	private KeyEventProducer controller = new KeyEventProducer() {
		{
			this.addKeyListening(Keyboard.KEY_SPACE);
			this.addKeyListening(MOUSE0);
		}
		
		long jumpPressTime;

		@Override
		public void onKeyDown(int key) {
			if(key == Keyboard.KEY_SPACE) {
				jumpPressTime = CritEngine.getVirtualTime();
			} else if(key == MOUSE0) {
				
			}
		}

		@Override
		public void onKeyFrame(int kid) {
			if(kid == Keyboard.KEY_SPACE) {
				
			} else if(kid == MOUSE0) {
				
			}
		}

		@Override
		public void onKeyUp(int kid) {
			if(kid == Keyboard.KEY_SPACE) {
				long dt = CritEngine.getVirtualTime() - jumpPressTime;
				if(dt > JUMP_CHARGE_TIME) dt = JUMP_CHARGE_TIME;
				double height = (0.3 + 0.7 * dt / JUMP_CHARGE_TIME) * JUMPING_HEIGHT;
				attemptJump(height);
			} else if(kid == MOUSE0) {
				
			}
		}
	};
	
	boolean isShooting; //是否在射击
	
	long jumpTime;
	double halfJumpLen; //跳跃时间的长度的一半
	double jumpHeight, currentHeight; //跳跃高度
	
	PlayerVel velProcess = new PlayerVel(this);
	PlayerCollider collider = new PlayerCollider();

	public EntityPlayer(Scene scene) {
		super(scene, -256, 100, JUDGEBOX_SIZE, JUDGEBOX_SIZE);
		
		this.addAttribute(velProcess);
		this.addAttribute(collider);
		this.setTexture(SceneGame.TEX_EDGE);
	}
	
	public void onFrameUpdate() {
		velProcess.frameUpdate(); //速度更新
		directioner.frameUpdate(); //上下键
		controller.frameUpdate(); //空格和A
		
		//如果玩家掉出屏幕……
		if(!this.getGeomProps().intersects(getScene().mainCamera.getGeomProps())) {
			getScene().gameOver();
		}
		
		AttrGeometry geom = this.getGeomProps();
		
		//更新y坐标
		double yChange = directioner.dirVertical * UPDOWN_SPEED * CritEngine.getTimer().getElapsedTime() / 1000D;
		geom.pos.y += yChange;
		if(geom.pos.y > SceneGame.SCENE_HEIGHT - JUDGEBOX_SIZE) {
			geom.pos.y = SceneGame.SCENE_HEIGHT - JUDGEBOX_SIZE;
		} else if(geom.pos.y < 0) {
			geom.pos.y = 0;
		}
		
		jumpUpdate();
		//System.out.println(geom.pos.y);
	}
	
	protected void jumpUpdate() {
		if(jumpTime != 0) {
			long dt = CritEngine.getVirtualTime() - jumpTime;
			if(dt > 2 * halfJumpLen) {
				jumpTime = 0;
				return;
			}
			double tmp = dt - halfJumpLen;
			currentHeight = -tmp * tmp * GRAVITY / 2 + jumpHeight;
		} else {
			currentHeight = 0;
		}
	}
	
	protected void attemptJump(double height) {
		if(jumpTime != 0) return; //还在跳跃呢你急啥;w;
		jumpTime = CritEngine.getVirtualTime();
		jumpHeight = height;
		halfJumpLen = Math.sqrt(2 * height / GRAVITY);
		currentHeight = 0;
	}
	
	
	public void drawEntity() {
		double scaleShadow = 0.5 * (1.0 - (currentHeight / JUMPING_HEIGHT)) + 0.5;
		Rect pos = this.getGeomProps();
		SceneGame scene = getScene();
		GL11.glPushMatrix(); {
			GL11.glTranslated(pos.getMinX() - 85, pos.getMinY(), 0);
			
			//阴影
			GL11.glPushMatrix(); {
				GL11.glTranslated(68, 6, 0);
				GL11.glTranslated(SHADE_SIZE / 2, SHADE_SIZE / 2, 0);
				GL11.glScaled(scaleShadow, scaleShadow, 1);
				CERenderEngine.bindTexture(SceneGame.TEX_PLAYER_SHADOW);
				GL11.glTranslated(-SHADE_SIZE / 2, -SHADE_SIZE / 2, 0);
				RenderUtils.renderTexturedQuads(0, 0, SHADE_SIZE, SHADE_SIZE);
			} GL11.glPopMatrix();
			
			//玩家
			GL11.glPushMatrix(); {
				GL11.glTranslated(0, 22 + currentHeight, 0);
				if(isShooting) {
					scene.animShooting.draw();
				} else {
					scene.animNormal.draw();
				}
			} GL11.glPopMatrix();
		} GL11.glPopMatrix();
		
		super.drawEntity();
	}
	
	protected void attemptShoot() {
		
	}
	
	private SceneGame getScene() {
		return (SceneGame) sceneObj;
	}

}
