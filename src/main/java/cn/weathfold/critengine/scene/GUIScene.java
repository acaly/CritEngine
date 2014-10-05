/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.Entity;

/**
 * @author WeAthFolD
 *
 */
public class GUIScene extends Scene {
	
	protected Set<Entity> elements = new HashSet<Entity>();

	@Override
	public Set<Entity> getSceneEntities() {
		return elements;
	}

	@Override
	public void renderScene() {
	}
	


}
