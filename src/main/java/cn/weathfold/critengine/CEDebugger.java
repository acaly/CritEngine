/**
 * 
 */
package cn.weathfold.critengine;

/**
 * 游戏内输出信息管理，以后大约会加入日志的支持
 * @author WeAthFolD
 */
public class CEDebugger {

	public static void fine(String msg) {
		System.out.println("[CE]" + msg);
	}

	public static void error(String msg) {
		System.err.println("[ERR]" + msg);

	}
}
