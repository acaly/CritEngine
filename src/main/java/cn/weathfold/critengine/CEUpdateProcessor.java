/**
 * 
 */
package cn.weathfold.critengine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.CEPhysicEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;

/**
 * 消息循环的接管。
 * @author WeAthFolD
 */
public class CEUpdateProcessor {
	
	private static Scene activeScene;
	
	private static Set<IEntityProcessor> entityProcessors = new HashSet<IEntityProcessor>();
	protected static boolean tickState = true; //当前tick是否需要继续更新
	
	static {
		entityProcessors.add(CEPhysicEngine.INSTANCE);
	}

	public static void frameUpdate(Scene scene) {
		activeScene = scene;
		
		CESoundEngine.frameUpdate();
		
		activeScene.frameUpdate();
		if(!tickState) {
			tickState = true;
			return;
		}
		
		List<Entity> list = activeScene.getSceneEntities();
		for(int i = 0; i < list.size(); ++i) {
			Entity e = list.get(i);
			if(e.deathFlag) {
				i--;
				list.remove(e); //回收实体 TODO:在ArrayList中大量删除是低效的，如何解决？
				continue;
			}
			
			e.onFrameUpdate();
			for(IEntityProcessor prc : entityProcessors) {
				prc.processEntity(e);
			}
			if(!tickState) {
				tickState = true;
				return;
			}
		}
		
		drawCall();
	}
	
	private static void drawCall() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPushMatrix();
		activeScene.renderBackground();
		activeScene.renderForeground();
		GL11.glPopMatrix();
	}
	
}
