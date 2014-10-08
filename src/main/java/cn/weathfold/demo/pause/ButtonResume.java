package cn.weathfold.demo.pause;

import cn.weathfold.demo.T24Button;
import cn.weathfold.demo.game.SceneGame;

public class ButtonResume extends T24Button
{
  public ButtonResume(ScenePause scene)
  {
    super(scene, 211.0D, 328.0D, 400.0D, 54.0D, "pause_resume0", "pause_resume1");
  }

  public void onButtonReleased()
  {
    super.onButtonReleased();
    gameScene().isPaused = false;
  }

  public void onFrameUpdate()
  {
    super.onFrameUpdate();
  }

  private SceneGame gameScene() {
    return ((ScenePause)this.sceneObj).gameScene;
  }
}