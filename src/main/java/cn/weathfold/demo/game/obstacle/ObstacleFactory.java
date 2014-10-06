/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import java.util.ArrayList;
import java.util.List;

import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.SceneGame;

/**
 * @author WeAthFolD
 *
 */
public class ObstacleFactory  {

	private static List<ObstacleTemplate> templateList = new ArrayList<ObstacleTemplate>();
	
	{
		//add templates
		templateList.add(new ObstacleTemplate(80, 80, 30, 20, SceneGame.TEX_DOGE));
	}
	
	public static ObstacleTemplate getTemplate(int i) {
		return templateList.get(i);
	}
	
	public static int getTemplateCount() {
		return templateList.size();
	}
}
