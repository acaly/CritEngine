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

	private static final double GEN_FROM = -800.0;
	private static final double GEN_AHEAD = 20000.0;
	private static final double GEN_STEP = 10000.0;

	private static final double GEN_MIN_Y = 0.0;
	private static final double GEN_MAX_Y = SceneGame.SCENE_HEIGHT;
	private static final double GEN_HEIGHT = GEN_MAX_Y - GEN_MIN_Y;
	
	private double lastGenTo;
	
	private List<IEntityTemplate> templateList = new ArrayList<IEntityTemplate>();
	private WeightedRandom weightedRandom = new WeightedRandom();
	private Random rand;
	
	public ObstacleFactory(SceneGame scene) {
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
	
	/**
	 * Add a new template
	 * @param template 
	 * @param weight The weight used in weighted random. Don't need to be normalized. 
	 *  (That is, the sum of weight in each template may not be 1.0.)
	 */
	public void addTemplate(IEntityTemplate template, double weight) {
		templateList.add(template);
		weightedRandom.addEntry(weight);
	}
	
	private double getSpacing(double x) {
		double s = Math.exp(x / 30000.0 + rand.nextDouble() * 0.3) * 300.0;
		return s + 50.0;
	}

	private double generateRange(double fromX, double toX) {
		double i;
		for (i = fromX; i > toX; i -= getSpacing(i)) {
			getTemplate(weightedRandom.next(rand.nextDouble()))
					.generate(i, rand.nextDouble() * GEN_HEIGHT + GEN_MIN_Y);
		}
		return i;
	}
	
	public void generateTo(double toX) {
		if (toX - GEN_AHEAD > lastGenTo) return;
		lastGenTo = generateRange(lastGenTo, lastGenTo - GEN_STEP);
	}
}
