/**
 * 
 */
package cn.weathfold.critengine;

import java.util.HashSet;
import java.util.Iterator;
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
		
		Iterator<Entity> iter = activeScene.getSceneEntities().iterator();
		while(iter.hasNext()) {
			Entity e = iter.next();
			if(e.deathFlag) {
				iter.remove(); //回收实体
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
