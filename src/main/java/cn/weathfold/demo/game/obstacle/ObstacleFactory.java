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

	private static final double GEN_FROM = 0;
	private static final double GEN_AHEAD = 2000.0;
	private static final double GEN_STEP = 1000.0;
	
	private List<ObstacleTemplate> templateList = new ArrayList<ObstacleTemplate>();
	
	private double lastGenTo = GEN_FROM;
	
	public ObstacleFactory(SceneGame scene) {
		//add templates
		templateList.add(new ObstacleTemplate(scene, 80, 80, 30, 20, SceneGame.TEX_DOGE, true));
	}
	
	public ObstacleTemplate getTemplate(int i) {
		return templateList.get(i);
	}
	
	public int getTemplateCount() {
		return templateList.size();
	}
	
	public void generateTo(double toX) {
		if (toX - GEN_AHEAD > lastGenTo) return;
		generateRange(lastGenTo - GEN_STEP, lastGenTo);
		lastGenTo = lastGenTo - GEN_STEP;
	}
	
	/**
	 * Generate obstacles in the range (fromX, toX). fromX must be smaller than toX.
	 * @param fromX
	 * @param toX
	 */
	private void generateRange(double fromX, double toX) {
		System.out.println("Gen range " + Double.toString(fromX) + ", " + Double.toString(toX));
		for (double i = fromX; i < toX; i += 100.0) {
			getTemplate(0).generate(i, 0.0);
		}
	}
}
