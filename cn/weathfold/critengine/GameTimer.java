/**
 * 
 */
package cn.weathfold.critengine;

/**
 * ʱ��ϵͳ�ļ򵥷�װ��������ͨ��ϵͳʱ���ȡ�Ļ����Ͻ���ʱ�����ͣ������
 * @author WeAthFolD
 *
 */
public class GameTimer {

	private long virtualTime = 0L;
	
	private boolean paused;
	private long deltaTime; //����ʱ���ϵͳʱ��Ĳ�ֵ
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
