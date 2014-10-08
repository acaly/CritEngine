/**
 * 
 */
package cn.weathfold.critengine.util;

import java.util.Map;

/**
 * 一些乱七八糟的辅助方法
 * @author WeAthFolD
 */
public class GenericUtils {

	/**
	 * map中的逆搜寻
	 */
	public static <T, U> T searchByValue(Map<T, U> map, U value) {
		for (Map.Entry<T, U> ent : map.entrySet()) {
			if (ent.getValue().equals(value))
				return ent.getKey();
		}
		return null;
	}

}
