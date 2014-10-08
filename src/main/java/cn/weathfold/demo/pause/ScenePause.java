package cn.weathfold.demo.pause;

import cn.weathfold.critengine.CEUpdateProcessor;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.game.SceneGame;

public class ScenePause extends Scene
{
  public SceneGame gameScene;
  public static final String TEX_MAIN = "pause_main";
  public static final String TEX_RESUME0 = "pause_resume0";
  public static final String TEX_RESUME1 = "pause_resume1";
  public static final String TEX_MAINMENU0 = "pause_mainmenu0";
  public static final String TEX_MAINMENU1 = "pause_mainmenu1";
  public static final String TEX_QUIT0 = "pause_quit0";
  public static final String TEX_QUIT1 = "pause_quit1";

  public ScenePause(SceneGame scene)
  {
    this.gameScene = scene;
    this.mainCamera = new Camera(this, 0.0D, 0.0D, 819.0D, 512.0D, Camera.Alignment.ALIGN_WIDTH);
    spawnEntity(new ButtonResume(this));
    spawnEntity(new ButtonMainmenu(this));
    spawnEntity(new ButtonQuit(this));
  }

  public void frameUpdate() {
    CEUpdateProcessor.manualUpdateEntities(this);
  }

  public void preloadResources(ResourcePool pool)
  {
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/back.png"), "pause_main");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/resume0.png"), "pause_resume0");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/resume1.png"), "pause_resume1");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/mainmenu0.png"), "pause_mainmenu0");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/mainmenu1.png"), "pause_mainmenu1");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/quit0.png"), "pause_quit0");
    pool.preloadTexture(new PNGTextureObject("/assets/type24//textures/pause/quit1.png"), "pause_quit1");
  }

  public void renderBackground() {
    CERenderEngine.bindTexture("pause_main");
    RenderUtils.renderTexturedQuads(153.5D, 0.0D, 665.5D, 512.0D);
  }
}