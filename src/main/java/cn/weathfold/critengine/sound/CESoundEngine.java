package cn.weathfold.critengine.sound;

import cn.weathfold.critengine.CEDebugger;
import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.scene.Scene;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

/**
 * 声音引擎，AL代码的包装，允许在任意时刻以简单的方法在场景中总播放声音。
 * 还没有完成。速度等功能暂不支持。
 * @author WeAthFolD
 */
public class CESoundEngine {

	private static FloatBuffer 
		listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F }), //侦听位置
		listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F }), //侦听速度
		listenerOri = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0F, 0.0F, 0.0F }); //侦听朝向

	private static Set<SourceData> playingSounds = new HashSet<SourceData>(); //存放播放音源的表

	private static int UPDATE_INTV = 2000; //检查并删除音源的间隔

	private static long lastUpdateTime = CritEngine.getVirtualTime();

	/**
	 * 在切换场景时调用，刷新播放数据库等。
	 */
	public static void refresh() {
		for (SourceData src : playingSounds) {
			AL10.alSourceStop(src.sourceID);
			AL10.alDeleteSources(src.sourceID);
		}
		playingSounds.clear();
	}

	/**
	 * 启动加载
	 */
	public static void init() {
		try {
			AL.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭时的清理
	 */
	public static void cleanup() {
		AL.destroy();
	}

	/**
	 * 播放全局声音（始终在listener中央）
	 * @param key 声音id
	 * @param attr 声音属性
	 * @return 生成的音源
	 */
	public static SourceData playGlobalSound(String key, SoundAttributes attr) {
		int sid = CEResourceHandler.querySoundId(key);

		if (sid == 0) {
			CEDebugger.error("Attempt playing an unregistered sound named "
					+ key);
			return null;
		}

		int buf = AL10.alGenSources();
		SourceData sd = new SourceData(true, attr, buf, attr.lifeTime);

		AL10.alSourcei(buf, 4105, sid);
		AL10.alSourcef(buf, 4099, attr.pitch);
		AL10.alSourcef(buf, 4106, attr.gain);
		AL10.alSource3f(buf, 4100, listenerPos.get(0), listenerPos.get(1), 0.0F);
		AL10.alSource3f(buf, 4102, 0.0F, 0.0F, 0.0F);

		AL10.alSourcePlay(buf);
		playingSounds.add(sd);
		return sd;
	}

	/**
	 * 播放一个世界中的声音
	 * @param key 声音id
	 * @param attr 声音属性
	 * @return 生成的音源
	 */
	public static SourceData playSound(String key, SoundEmitter attr) {
		int sid = CEResourceHandler.querySoundId(key);
		if (sid == 0) {
			CEDebugger.error("Attempt playing an unregistered sound named "
					+ key);
			return null;
		}

		int buf = AL10.alGenSources();
		SourceData sd = new SourceData(true, attr, buf, attr.lifeTime);

		AL10.alSourcei(buf, 4105, sid);
		AL10.alSourcef(buf, 4099, attr.pitch);
		AL10.alSourcef(buf, 4106, attr.gain);
		AL10.alSource3f(buf, 4100, attr.pos.x, attr.pos.y, 0.0F);
		AL10.alSource3f(buf, 4102, attr.vel.x, attr.vel.y, 0.0F);

		AL10.alSourcePlay(buf);
		playingSounds.add(sd);
		return sd;
	}

	public static void resumeSound(SourceData data) {
		if (!playingSounds.contains(data))
			return;
		AL10.alSourcePause(data.sourceID);
	}

	public static void stopSound(SourceData data) {
		if (!playingSounds.contains(data))
			return;
		AL10.alSourceStop(data.sourceID);
	}

	public static void pauseSound(SourceData data) {
		if (!playingSounds.contains(data))
			return;
		AL10.alSourcePause(data.sourceID);
	}

	/**
	 * 每帧更新
	 */
	public static void frameUpdate() {
		listenerPos.clear();
		Scene scene = CritEngine.getCurrentScene();

		long time = CritEngine.getVirtualTime();

		if (scene == null) {
			listenerPos.put(new float[] { 0.0F, 0.0F, 0.0F });
		} else {
			AttrGeometry pos = (AttrGeometry) scene.mainCamera
					.getAttribute("geometry");
			listenerPos
					.put(new float[] {
							(float) (pos.getMinX() + pos.getWidth() / 2.0D),
							(float) (pos.getMinY() + pos.getHeight() / 2.0D), 0.0F });
		}

		AL10.alListener3f(4100, listenerPos.get(0), listenerPos.get(1),
				listenerPos.get(2));
		AL10.alListener3f(4111, -1.0F, 0.0F, 0.0F);

		if (time - lastUpdateTime > UPDATE_INTV) {
			lastUpdateTime = time;

			Iterator<SourceData> iter = playingSounds.iterator();
			while (iter.hasNext()) {
				SourceData dt = iter.next();
				if (time - dt.creationTime > dt.lifeTime) {
					AL10.alSourceStop(dt.sourceID);
					AL10.alDeleteSources(dt.sourceID);
					iter.remove();
				} else if (dt.isGlobal) {
					AL10.alSource3f(dt.sourceID, 4100, listenerOri.get(0),
							listenerOri.get(1), 0.0F);
				}
			}
		}
	}

	public static class SourceData {
		public final boolean isGlobal;
		public final SoundAttributes attr;
		public final int sourceID;
		public final int lifeTime;
		public final long creationTime;

		public SourceData(boolean global, SoundAttributes at, int srcID,
				int lifeTime) {
			this.isGlobal = global;
			this.attr = at;
			this.sourceID = srcID;
			this.lifeTime = lifeTime;
			this.creationTime = CritEngine.getVirtualTime();
		}
	}
}