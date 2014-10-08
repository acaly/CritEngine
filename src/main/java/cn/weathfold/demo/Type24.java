package cn.weathfold.demo;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.demo.menu.SceneMenu;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Type24
{
  public static final String ASSETS_PATH = "/assets/type24/";
  public static final String SND_BUTTON_CLICK = "btnclick";
  public static final String SND_BUTTON_RELEASE = "/assets/type24/btnrelease";
  public static final float QUAD_ALIGN = 0.1499025F;
  public static final boolean DEBUG = true;

  public static void main(String[] args)
  {
    new Type24().run();
  }

  public void run() {
    Display.setTitle("CritEngine : Type 24");
    try
    {
      Display.setDisplayMode(new DisplayMode(819, 512));
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
    CERenderEngine.setDefaultFontSize(64);
    CESoundEngine.setDistScale(1.0E-004F);
    CritEngine.start(new SceneMenu());
  }
}