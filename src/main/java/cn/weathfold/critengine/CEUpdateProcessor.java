package cn.weathfold.critengine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.CEPhysicEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;

/**
 * 全局的帧更新处理
 * @author WeAthFolD
 */
public class CEUpdateProcessor {
	private static Scene activeScene; //当前活跃的场景
	private static Set<IEntityProcessor> entityProcessors = new HashSet<IEntityProcessor>(); //实体处理器列表
	protected static boolean tickState = true; //当前tick是否处于正常状态？

	static {
		entityProcessors.add(CEPhysicEngine.INSTANCE);
	}

	/**
	 * 进行每帧更新
	 */
	public static void frameUpdate(Scene scene) {
		activeScene = scene;

		CESoundEngine.frameUpdate();

		activeScene.frameUpdate();
		if (!tickState) {
			tickState = true;
			return;
		}

		List<Entity> list = activeScene.getSceneEntities();
		manualUpdateEntities(list, activeScene);

		drawCall();
	}

	/**
	 * 手动更新场景中的实体，通常在场景使用内部场景的情况时使用
	 * TODO 还有更优雅的方法么？
	 * @param scene
	 */
	public static void manualUpdateEntities(Scene scene) {
		manualUpdateEntities(scene.getSceneEntities(), scene);
	}

	/**
	 * 手动更新一批实体
	 */
	public static void manualUpdateEntities(List<Entity> list, Scene scene) {
		for (int i = 0; i < list.size(); i++) {
			Entity e = list.get(i);
			if (scene.doesUpdate(e)) {
				if (e.deathFlag) {
					i--;
					list.remove(e);
				} else {
					e.onFrameUpdate();
					for (IEntityProcessor prc : entityProcessors) {
						prc.processEntity(e);
					}
					if (!tickState) {
						tickState = true;
						return;
					}
				}
			}
		}
	}

	/**
	 * 进行绘制
	 */
	private static void drawCall() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPushMatrix(); {
			activeScene.renderBackground();
			activeScene.renderForeground();
		} GL11.glPopMatrix();
	}
}
