/**
 * 
 */
package cn.weathfold.critengine.scene;

import java.util.HashSet;
import java.util.Set;

import cn.weathfold.critengine.entity.Entity;

/**
 * @author WeAthFolD
 *
 */
public class GUIScene extends Scene {
	
	protected Set<Entity> elements = new HashSet();
	
	public GUIScene() {
		
	}

	@Override
	public Set<Entity> getSceneEntities() {
		return elements;
	}

	@Override
	public void renderScene() {

	}

}
