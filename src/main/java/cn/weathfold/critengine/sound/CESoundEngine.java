/**
 * 
 */
package cn.weathfold.critengine.sound;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.scene.Scene;

/**
 * @author WeAthFolD
 *
 */
public class CESoundEngine {

	private static float distanceScale = 1.0F;
	
	@SuppressWarnings("unused")
	private static FloatBuffer
		listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] {0, 0, 0}),
		listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] {0, 0, 0}), //reserved
		listenerOri = BufferUtils.createFloatBuffer(3).put(new float[] {0, 0, 0}); //reserved
	
	public static class SourceData {
		public final boolean isGlobal;
		public final SoundAttributes attr;
		public final int sourceID;
		public final int lifeTime; //生命期 in ms
		public final long creationTime;
		
		public SourceData(boolean global, SoundAttributes at, int srcID, int lifeTime) {
			isGlobal = global;
			attr = at;
			sourceID = srcID;
			this.lifeTime = lifeTime;
			creationTime = CritEngine.getVirtualTime();
		}
	}
	
	private static Set<SourceData> playingSounds = new HashSet<SourceData>();
	
	/* gc every 2s */
	private static int UPDATE_INTV = 2000;
	
	private static long lastUpdateTime = CritEngine.getVirtualTime();
	
	/**
	 *  设置衰减距离的缩放比例 */
	public static void setDistScale(float f) {
		distanceScale = f;
	}
	
	/**
	 *  获取衰减距离的缩放比例
	 */
	public static float getDistScale() {
		return distanceScale;
	}
	
	public static void refresh() {
		
		
	}
	
	public static void init() {
		//AL10.alGenBuffers();
	}
	
	public static SourceData playGlobalSound(String key, SoundAttributes attr) {
		int sid = CEResourceHandler.querySoundId(key);
		if(sid == 0) {
			throw new RuntimeException("Attempt playing an unregistered sound named " + key);
		}
		
		int buf = AL10.alGenSources();
		SourceData sd = new SourceData(true, attr, buf, 1000);
		
		//设置参数
		AL10.alSourcei(buf, AL10.AL_BUFFER, sid);
		AL10.alSourcef(buf, AL10.AL_PITCH, attr.pitch);
		AL10.alSourcef(buf, AL10.AL_GAIN, attr.gain);
		AL10.alSource3f(buf, AL10.AL_POSITION, listenerPos.get(0), listenerPos.get(1), 0);
		AL10.alSource3f(buf, AL10.AL_VELOCITY, 0, 0, 0);
		
		AL10.alSourcePause(buf);;
		playingSounds.add(sd);
		return sd;
	}
	
	public static SourceData playSound(String key, SoundEmitter attr) {
		int sid = CEResourceHandler.querySoundId(key);
		if(sid == 0) {
			throw new RuntimeException("Attempt playing an unregistered sound named " + key);
		}
		
		int buf = AL10.alGenSources();
		attr.pos.x *= distanceScale;
		attr.pos.y *= distanceScale;
		SourceData sd = new SourceData(true, attr, buf, 1000);
		
		//设置参数
		AL10.alSourcei(buf, AL10.AL_BUFFER, sid);
		AL10.alSourcef(buf, AL10.AL_PITCH, attr.pitch);
		AL10.alSourcef(buf, AL10.AL_GAIN, attr.gain);
		AL10.alSource3f(buf, AL10.AL_POSITION, attr.pos.x, attr.pos.y, 0);
		AL10.alSource3f(buf, AL10.AL_VELOCITY, attr.vel.x, attr.vel.y, 0);
		
		AL10.alSourcePause(buf);;
		playingSounds.add(sd);
		return sd;
	}
	
	public static void resumeSound(SourceData data) {
		if(!playingSounds.contains(data))
			return;
		AL10.alSourcePause(data.sourceID);
	}
	
	public static void stopSound(SourceData data) {
		if(!playingSounds.contains(data))
			return;
		AL10.alSourceStop(data.sourceID);
	}
	
	public static void pauseSound(SourceData data) {
		if(!playingSounds.contains(data))
			return;
		AL10.alSourcePause(data.sourceID);
	}
	
	/**
	 * 帧更新
	 */
	public static void frameUpdate() {
		
		//进行位置更新
		listenerPos.clear();
		Scene scene = CritEngine.getCurrentScene();
		
		long time = CritEngine.getVirtualTime();
		
		if(scene == null) {
			listenerPos.put(new float[] {0, 0, 0});
		} else {
			//更新到主相机所在位置
			AttrGeometry pos = (AttrGeometry) scene.mainCamera.getAttribute("geometry");
			listenerPos.put(new float[] {
					(float)pos.getMinX() * distanceScale,
					(float)pos.getMinY() * distanceScale,
					0
			});
		}
		
		//置入缓存
		//AL10.alListener(AL10.AL_POSITION, listenerPos);
		
		
		//声音更新，和生命期检查
		if(time - lastUpdateTime > UPDATE_INTV) {
			lastUpdateTime = time;
			
			Iterator<SourceData> iter = playingSounds.iterator();
			while(iter.hasNext()) {
				SourceData dt = iter.next();
				if(time - dt.creationTime > dt.lifeTime) {
					AL10.alSourceStop(dt.sourceID);
					AL10.alDeleteSources(dt.sourceID);
					iter.remove();
				} else {
					
					if(dt.isGlobal) { //更新静态声音所在位置
						AL10.alSource3f(dt.sourceID, AL10.AL_POSITION, listenerOri.get(0), listenerOri.get(1), 0);
					}
					
				}
			}
		}
	 	
		
	}
}
