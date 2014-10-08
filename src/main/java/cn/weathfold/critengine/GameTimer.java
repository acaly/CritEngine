/**
 * 
 */
package cn.weathfold.critengine;

/**
 * 时间的简单封装，允许在通过系统时间获取的基础上进行时间的暂停操作。
 * @author WeAthFolD
 */
public class GameTimer {

	private long virtualTime = 0L;

	private boolean paused;
	private long deltaTime; // 虚拟时间和系统时间的差值
	private long lastSystemTime;
	private long elapsedTime;

	public GameTimer() {
		updateTime();
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void updateTime() {
		long rt = System.nanoTime() / 1000000L;
		if (!paused) {
			virtualTime = rt - deltaTime;
		} else {
			deltaTime += rt - lastSystemTime;
		}
		// System.out.println("Virtual time " + virtualTime);
		elapsedTime = rt - lastSystemTime;
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
