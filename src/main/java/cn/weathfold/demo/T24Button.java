package cn.weathfold.demo;

import cn.weathfold.critengine.entity.gui.GUIButton;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundAttributes;

public class T24Button extends GUIButton
{
  private boolean hovering = true;
  String texHovering;
  String texNormal;

  public T24Button(Scene scene, double x, double y, double width, double height, String texture0, String texture1)
  {
    super(scene, x, y, width, height);
    this.texNormal = texture0;
    this.texHovering = texture1;
    setTexture(this.texNormal);
  }

  public void onFrameUpdate()
  {
    boolean b = isMouseInEntity();
    if ((!this.hovering) && (b)) {
      CESoundEngine.playGlobalSound("btnclick", new SoundAttributes(100));
    }
    this.hovering = b;

    super.onFrameUpdate();
  }

  public void onButtonPressed()
  {
    CESoundEngine.playGlobalSound("/assets/type24/btnrelease", new SoundAttributes(300));
  }

  public void onButtonFrame()
  {
  }

  public void onButtonReleased()
  {
    CESoundEngine.playGlobalSound("/assets/type24/btnrelease", new SoundAttributes(300));
  }

  public void drawEntity()
  {
    this.textureID = (this.hovering ? this.texHovering : this.texNormal);
    super.drawEntity();
  }
}