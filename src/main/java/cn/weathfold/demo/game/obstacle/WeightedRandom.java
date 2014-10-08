package cn.weathfold.demo.game.obstacle;

import java.util.ArrayList;

/**
 * @author acaly
 */
public class WeightedRandom {
	
	private ArrayList<Double> entries = new ArrayList<Double>();
	private double weightSum = 0.0;

	public void addEntry(double weight) {
		weightSum += weight;
		entries.add(weightSum);
	}

	public int size() {
		return entries.size();
	}

	/**
	 * Get next random result
	 * 
	 * @param d
	 *            a random number with the same range as Random.nextDouble()
	 * @return the index of entry that has been hit (0 to size() - 1)
	 */
	public int next(double d) {
		d *= weightSum;
		int i;
		for (i = 0; entries.get(i) < d && i < entries.size() - 1; ++i)
			;
		return i;
	}
}
