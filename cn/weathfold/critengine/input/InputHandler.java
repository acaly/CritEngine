package cn.weathfold.critengine.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import cn.weathfold.critengine.util.GenericUtils;

/**
 * 操作控制的总线。
 * 客户端程序应该向这里注册键位号及其映射到的键盘/鼠标ID。
 * 实际的侦听操作主要通过对此类的查询来进行。
 */
public class InputHandler {
	
	private static class KeyStatus {
		public int id;
		public boolean isDown;
		private long createTime;
		
		public KeyStatus(int i) {
			id = i;
			createTime = System.currentTimeMillis();
		}
		
		public boolean equals(Object obj) {
			if(obj == null || !(obj instanceof KeyStatus))
				return false;
			return id == ((KeyStatus)obj).id;
		}
	}
	
	public static final int 
		KID_MOUSE1 = -100,
		KID_MOUSE2 = -101,
		KID_MOUSE3 = -103;
	
	private static final int
		PROTECTION_TIME = 500;
	
	public static Map<Integer, KeyStatus> 
		statsMap = new HashMap<Integer, KeyStatus>();
	
	public static void registerKey(int subkey, int key) {
		removeKey(subkey);
		statsMap.put(key, new KeyStatus(subkey));
	}
	
	public static void removeKey(int subKey) {
		Entry<Integer, KeyStatus> ent = GenericUtils.searchByValue(statsMap, new KeyStatus(subKey));
		if(ent != null)
			statsMap.remove(ent.getKey());
	}
	
	/**
	 * 判断某个键是否被按下。
	 */
	public static boolean isKeyDown(int subKey) {
		return GenericUtils.searchByValue(statsMap, new KeyStatus(subKey)).getValue().isDown;
	}
	
	public static void frameUpdate() {
		long time = System.currentTimeMillis();
		while(Keyboard.next()) {
			int kb = Keyboard.getEventKey();
			KeyStatus ks = statsMap.get(kb);
			if(ks == null) {
				return;
			}
			
			boolean kd = Keyboard.getEventKeyState();
			ks.isDown = kd;
			if(time - ks.createTime <= PROTECTION_TIME) {
				return;
			}
			
			if(kd) {
				//Send the events
			}
		}
	}
	
}
