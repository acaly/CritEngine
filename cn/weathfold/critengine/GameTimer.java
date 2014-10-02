/**
 * 
 */
package cn.weathfold.critengine;

/**
 * 时间系统的简单封装，允许在通过系统时间获取的基础上进行时间的暂停操作。
 * @author WeAthFolD
 *
 */
public class GameTimer {

	private long virtualTime = 0L;
	
	private boolean paused;
	private long deltaTime; //虚拟时间和系统时间的差值
	private long lastSystemTime;
	
	public GameTimer() {
		updateTime();
	}
	
	public void updateTime() {
		long rt = System.currentTimeMillis();
		if(!paused) {
			virtualTime = rt - deltaTime;
		} else {
			deltaTime += rt - lastSystemTime;
		}
		lastSystemTime = rt;
	}
	
	public long getTime() {
		return virtualTime;
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}
	
	public boolean isPaused() {
		return paused;
	}
}
