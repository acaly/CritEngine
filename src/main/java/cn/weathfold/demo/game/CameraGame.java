package cn.weathfold.demo.game;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.GameTimer;
import cn.weathfold.critengine.camera.Camera;
import cn.weathfold.critengine.camera.Camera.Alignment;
import cn.weathfold.critengine.entity.attribute.AttrGeometry;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import cn.weathfold.critengine.util.Vector2d;
import cn.weathfold.demo.game.player.EntityPlayer;

public class CameraGame extends Camera
{
  public CameraGame(Scene scene)
  {
    super(scene, -512.0D, 0.0D, 819.0D, 512.0D, Camera.Alignment.ALIGN_WIDTH);
    
  }

  public void onFrameUpdate()
  {
    if (getScene().gameOver) {
      return;
    }
    gp().pos.x -= EntityPlayer.SPEED_NORMAL * CritEngine.getTimer().getElapsedTime() / 1000.0D;
  }
  
  public AttrGeometry gp() {
	  return super.getGeomProps();
  }
  
  // test

  public void resetPosition() {
    Rect rect = gp();
    rect.pos.x = -512.0D;
  }

  private SceneGame getScene() {
    return (SceneGame)this.sceneObj;
  }
}