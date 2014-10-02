/**
 * 
 */
package cn.weathfold.critengine.util;

import java.util.Map;

/**
 * @author WeAthFolD
 *
 */
public class GenericUtils {

	public static <T, U> Map.Entry<T, U> searchByValue(Map<T, U> map, U value) {
		for(Map.Entry<T, U> ent : map.entrySet()) {
			if(ent.getValue().equals(value))
				return ent;
		}
		return null;
	}
	
}
