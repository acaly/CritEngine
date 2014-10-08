package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.render.animation.LoopAnimation;
import cn.weathfold.critengine.resource.CEResourceHandler;
import cn.weathfold.critengine.resource.PNGTextureObject;
import cn.weathfold.critengine.resource.ResourcePool;
import cn.weathfold.critengine.resource.WavSoundObject;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;
import cn.weathfold.critengine.util.Rect;
import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class SceneMenu extends Scene
{
  public static SceneMenu INSTANCE;
  protected static final String SND_BACK = "BGM";
  protected static final String TEX_ANIM = "back";
  protected static final String TEX_START = "start0";
  protected static final String TEX_START_ACTIVATED = "start1";
  protected static final String TEX_QUIT = "quit0";
  protected static final String TEX_QUIT_ACTIVATED = "quit1";
  protected static final String TEX_TITLE = "title";
  private String[] ANIM_MAIN;
  private LoopAnimation backAnim;
  private Random rand = new Random();
  private boolean played = false;
  private long lastPlayTick;
  private static boolean firstLoad = true;

  public SceneMenu() {
    this.mainCamera = new Camera(this, -153.5D, 0.0D, 819.0D, 512.0D, 
      Camera.Alignment.ALIGN_WIDTH);
    this.elements.add(new EntityTitle(this));
    this.elements.add(new ButtonStart(this));
    this.elements.add(new ButtonExit(this));
    if (INSTANCE == null)
      INSTANCE = this;
  }

  public void frameUpdate()
  {
    if (CritEngine.getVirtualTime() - this.lastPlayTick > 92000L) {
      this.played = false;
    }

    if (!this.played) {
      this.played = true;
      CESoundEngine.playGlobalSound("BGM", new SoundAttributes(92000));
      this.lastPlayTick = CritEngine.getVirtualTime();
    }
  }

  public void preloadResources(ResourcePool pool)
  {
    if (firstLoad)
    {
      CEResourceHandler.globalPreloadSound(new WavSoundObject(
        "/assets/type24/sounds/buttonclick.wav"), 
        "btnclick");
      CEResourceHandler.globalPreloadSound(new WavSoundObject(
        "/assets/type24/sounds/buttonclickrelease.wav"), 
        "/assets/type24/btnrelease");

      firstLoad = false;
    }

    String[] anim = new String[12];
    for (int i = 0; i < 12; i++) {
      anim[i] = ("/assets/type24/textures/menu/back" + i + ".png");
    }
    this.ANIM_MAIN = pool.preloadTextureArray(PNGTextureObject.readArray(anim), 
      "back");
    this.backAnim = new LoopAnimation(this.ANIM_MAIN)
    {
      protected int nextFrame()
      {
        return SceneMenu.this.rand.nextInt(12);
      }
    }
    .setFrameInterval(300).setDrawingQuad(new Rect(153.5D, 0.0D, 512.0D, 512.0D));

    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/menu/exit.png"), 
      "quit0");
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/menu/exit_activated.png"), 
      "quit1");
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/menu/start.png"), 
      "start0");
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/menu/start_activated.png"), 
      "start1");
    pool.preloadTexture(new PNGTextureObject("/assets/type24/textures/menu/title.png"), 
      "title");

    pool.preloadSound(new WavSoundObject("/assets/type24/sounds/menu/bgm.wav"), 
      "BGM");
  }

  public void onSwitchedScene()
  {
    this.lastPlayTick = 0L;
  }

  public boolean keepResourcePool()
  {
    return true;
  }

  public void renderBackground()
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.backAnim.draw();
  }
}