package cn.weathfold.demo.pause;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.menu.SceneMenu;

public class ButtonMainmenu extends T24Button
{
  public ButtonMainmenu(Scene scene)
  {
    super(scene, 211.0D, 231.0D, 400.0D, 54.0D, "pause_mainmenu0", "pause_mainmenu1");
  }

  public void onButtonReleased()
  {
    CritEngine.switchScene(SceneMenu.INSTANCE);
    super.onButtonReleased();
  }
}