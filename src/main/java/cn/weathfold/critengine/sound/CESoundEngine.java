package cn.weathfold.critengine.sound;

import cn.weathfold.critengine.CEDebugger;
import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
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
import org.lwjgl.util.vector.Vector2f;

public class CESoundEngine {
	private static float distanceScale = 0.005F;

	private static FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3)
			.put(new float[] { 0.0F, 0.0F, 0.0F });
	private static FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3)
			.put(new float[] { 0.0F, 0.0F, 0.0F });
	private static FloatBuffer listenerOri = BufferUtils.createFloatBuffer(3)
			.put(new float[] { 0.0F, 0.0F, 0.0F });

	private static Set<SourceData> playingSounds = new HashSet<SourceData>();

	private static int UPDATE_INTV = 2000;

	private static long lastUpdateTime = CritEngine.getVirtualTime();

	public static void setDistScale(float f) {
		distanceScale = f;
	}

	public static float getDistScale() {
		return distanceScale;
	}

	public static void refresh() {
		for (SourceData src : playingSounds) {
			AL10.alSourceStop(src.sourceID);
			AL10.alDeleteSources(src.sourceID);
		}
		playingSounds.clear();
	}

	public static void init() {
		try {
			AL.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanup() {
		AL.destroy();
	}

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

	public static SourceData playSound(String key, SoundEmitter attr) {
		int sid = CEResourceHandler.querySoundId(key);
		if (sid == 0) {
			CEDebugger.error("Attempt playing an unregistered sound named "
					+ key);
			return null;
		}

		int buf = AL10.alGenSources();
		attr.pos.x *= distanceScale;
		attr.pos.y *= distanceScale;
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
							(float) (pos.getMinX() + pos.width / 2.0D)
									* distanceScale,
							(float) (pos.getMinY() + pos.height / 2.0D)
									* distanceScale, 0.0F });
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