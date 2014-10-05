/**
 * 
 */
package cn.weathfold.client;

import java.util.Set;

import org.lwjgl.opengl.GL11;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.GUIScene;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;

/**
 * @author WeAthFolD
 *
 */
public class SceneMain extends GUIScene {
	
	public static final String
		TEX_BACK = "back",
		TEX_BTN0 = "btn0",
		TEX_BTN1 = "btn1";
	
	public SceneMain() {
		this.mainCamera = 
				new Camera(this, 0, 0, 1138, 640, Alignment.ALIGN_WIDTH);
		this.elements.add(new GUIButton(this, 130, 217, 352, 72) {

			@Override
			public void onButtonPressed() {
				System.out.println("GUIButton pressed");
			}

			@Override
			public void onButtonFrame() {
			}

			@Override
			public void onButtonReleased() {
				CritEngine.switchScene(new ClientRunner.MyScene());
			}
			
			public void drawEntity() {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				CERenderEngine.bindTexture(this.isMouseInEntity() ? TEX_BTN1 : TEX_BTN0);
				RenderUtils.renderTexturedQuads((Rect) this.getAttribute("geometry"));
			}
		
		});
	}
	
	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.scene.Scene#renderScene()
	 */
	@Override
	public void renderScene() {
		// TODO Auto-generated method stub

	}
	
	public void renderBackground() {
		CERenderEngine.bindTexture(TEX_BACK);
		RenderUtils.renderTexturedQuads(0, 0, 1, 1);
	}
	
	public boolean keepResourcePool() {
		return true;
	}
	
	public void preloadResources(ResourcePool pool) {
		pool.preloadTexture(new PNGTextureObject("/assets/textures/main.png"), TEX_BACK);
		pool.preloadTexture(new PNGTextureObject("/assets/textures/button.png"), TEX_BTN0);
		pool.preloadTexture(new PNGTextureObject("/assets/textures/button_activated.png"), TEX_BTN1);
	}
	
	

}
