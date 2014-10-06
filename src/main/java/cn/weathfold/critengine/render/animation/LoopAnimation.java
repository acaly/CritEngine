package cn.weathfold.critengine.render.animation;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.util.Rect;

/**
 * 几张png序列形成的循环动画。在每帧调用的话会自己进行时序控制。
 * @author WeAthFolD
 *
 */
public class LoopAnimation {
	
	int texArray[]; // 贴图texID的序列 
	int currentFrame = 0; // 当前帧id 
	int frameInterval = 100; // 帧切换的间隔(毫秒)
	long lastChangeTime;
	Rect drawArea = new Rect(0, 0, 1, 1);

	public LoopAnimation(String[] textures) {
		texArray = new int[textures.length];
		for(int i = 0; i < texArray.length; ++i) {
			texArray[i] = CEResourceHandler.queryTextureId(textures[i]);
		}
	}
	
	public LoopAnimation(int[] textures) {
		texArray = textures;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public void setCurrentFrame(int id) {
		currentFrame = id;
	}
	
	public LoopAnimation setFrameInterval(int i) {
		frameInterval = i;
		return this;
	}
	
	public LoopAnimation setDrawingQuad(Rect rect) {
		drawArea = rect;
		return this;
	}
	
	public void draw() {
		if(lastChangeTime == 0) {
			lastChangeTime = CritEngine.getVirtualTime();
		}
		
		long time = CritEngine.getVirtualTime();
		if(time - lastChangeTime > frameInterval) {
			lastChangeTime = time;
			currentFrame = nextFrame();
		}
		
		GL11.glPushMatrix(); {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texArray[currentFrame]);
			
			drawObject();
		} GL11.glPopMatrix();
	}
	
	/**
	 * 获取下一帧，覆盖这个函数来实现微妙的动画效果
	 */
	protected int nextFrame() {
		return currentFrame == texArray.length - 1 ? 0 : currentFrame + 1;
	}
	
	/* 绘制几何体，默认为正交矩形，覆盖这个函数来实现自定义绘制 */
	protected void drawObject() {
		RenderUtils.renderTexturedQuads(drawArea);
	}

}
