package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.game.SceneGame;

public class ButtonStart extends T24Button
{
  private static String NORMAL = "start0";
  private static String ACTIVATED = "start1";

  public ButtonStart(Scene scene)
  {
    super(scene, 10.0D, 52.0D, 220.0D, 40.0D, NORMAL, ACTIVATED);
  }

  public void onButtonReleased()
  {
    CESoundEngine.refresh();

    super.onButtonReleased();
    CritEngine.switchScene(SceneGame.INSTANCE == null ? new SceneGame() : SceneGame.INSTANCE);
  }
}