package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;

public class ButtonExit extends T24Button
{
  private static final String NORMAL = "quit0";
  private static final String ACTIVATED = "quit1";

  public ButtonExit(Scene scene)
  {
    super(scene, 10.0D, 12.0D, 220.0D, 40.0D, "quit0", "quit1");
    setTexture("quit0");
  }

  public void onButtonFrame()
  {
  }

  public void onButtonReleased() {
    CritEngine.switchScene(null);
  }
}