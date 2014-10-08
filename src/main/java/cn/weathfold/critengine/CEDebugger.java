/**
 * 
 */
package cn.weathfold.critengine;

/**
 * @author FolD
 *
 */
public class CEDebugger {
	
	public static void fine(String msg) {
		System.out.println("[CE]" + msg);
	}
	
	public static void error(String msg) {
		System.err.println("[ERR]" + msg);
		
	}
}
