package cn.weathfold.demo.over;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.input.KeyEventProducer;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.menu.SceneMenu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class SceneGameOver extends Scene
{
  protected static final String TEX_MAIN = "over_main";
  protected static final String TEX_INFO = "over_info";
  int score;
  EntityInfo info = new EntityInfo(this);

  private KeyEventProducer keyListener = new KeyEventProducer()
  {
	  {
		  this.addKeyListening(Keyboard.KEY_ESCAPE);
	  }
	  
    public void onKeyDown(int kid)
    {
    }

    public void onKeyFrame(int kid)
    {
    }

    public void onKeyUp(int kid)
    {
      CritEngine.switchScene(SceneMenu.INSTANCE);
    }
  };
  SceneGame gameScene;
  Color fontColor = new Color(255, 240, 193, 255);

  public SceneGameOver(SceneGame scene)
  {
    this.gameScene = scene;
  }

  public void setScore(int i) {
    this.score = i;
  }

  public void frameUpdate()
  {
    this.keyListener.frameUpdate();
  }

  public void renderBackground()
  {
    GL11.glDisable(3553);
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
    GL11.glPushMatrix();
    RenderUtils.renderTexturedQuads(0.0D, 0.0D, 819.0D, 512.0D);
    GL11.glPopMatrix();
    GL11.glEnable(3553);
  }

  public void renderForeground()
  {
    GL11.glPushMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    CERenderEngine.bindTexture("over_main");
    RenderUtils.renderTexturedQuads(153.5D, 0.0D, 665.5D, 512.0D);

    float sz = 60.0F;
    CERenderEngine.switchFont("Coppplate Gothic Bold");
    String str = String.valueOf(this.gameScene.currentScore);
    double len = CERenderEngine.getStringLength(str, 60.0F);
    CERenderEngine.drawString(409.5D - len / 2.0D, 250.0D, str, 60.0F, this.fontColor);

    this.info.drawEntity();
    GL11.glPopMatrix();
  }

  public void preloadResources(ResourcePool pool) {
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/over/main.png"), "over_main");
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/over/info.png"), "over_info");
    CERenderEngine.preloadFont("Copperplate Gothic Bold");
  }
}