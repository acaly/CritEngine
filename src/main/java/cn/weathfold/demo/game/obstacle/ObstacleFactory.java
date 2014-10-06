/**
 * 
 */
package cn.weathfold.demo.game.obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.demo.game.SceneGame;

/**
 * @author WeAthFolD, acaly
 *
 */
public class ObstacleFactory  {

	private static final double GEN_FROM = 0;
	private static final double GEN_AHEAD = 20000.0;
	private static final double GEN_STEP = 10000.0;

	private static final double GEN_MIN_Y = 0.0;
	private static final double GEN_MAX_Y = SceneGame.SCENE_HEIGHT;
	private static final double GEN_HEIGHT = GEN_MAX_Y - GEN_MIN_Y;
	
	private double lastGenTo;
	
	private List<IEntityTemplate> templateList = new ArrayList<IEntityTemplate>();
	private Random rand;
	
	public ObstacleFactory(SceneGame scene) {
		//templateList.add(new ObstacleTemplate(scene, 80, 80, 30, 20, SceneGame.TEX_DOGE, true));
		//templateList.add(new ObstacleTemplate(scene, 80, 80, 30, 20, SceneGame.TEX_OBSTACLES[0], true));
		lastGenTo = GEN_FROM; 
		rand = new Random();
	}
	
	public void resetStat() {
		lastGenTo = GEN_FROM;
	}
	
	public IEntityTemplate getTemplate(int i) {
		return templateList.get(i);
	}
	
	public int getTemplateCount() {
		return templateList.size();
	}
	
	public void addTemplate(IEntityTemplate template) {
		templateList.add(template);
	}
	
	private double getSpacing(double x) {
		double s = Math.exp(x / 30000.0 + rand.nextDouble() * 0.3) * 400.0;
		return s + 50.0;
	}

	private double generateRange(double fromX, double toX) {
		//System.out.println("Gen range " + Double.toString(fromX) + ", " + Double.toString(toX));
		double i;
		for (i = fromX; i > toX; i -= getSpacing(i)) {
			getTemplate(rand.nextInt(getTemplateCount())).generate(i, rand.nextDouble() * GEN_HEIGHT + GEN_MIN_Y);
		}
		return i;
	}
	
	public void generateTo(double toX) {
		if (toX - GEN_AHEAD > lastGenTo) return;
		//System.out.println("Current at " + Double.toString(toX));
		lastGenTo = generateRange(lastGenTo, lastGenTo - GEN_STEP);
	}
}
