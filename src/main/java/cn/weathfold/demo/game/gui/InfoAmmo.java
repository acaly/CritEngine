package cn.weathfold.demo.game.gui;

import cn.weathfold.critengine.entity.gui.GUIComponent;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;
import org.newdawn.slick.Color;

public class InfoAmmo extends GUIComponent
{
  SceneGame sceneGame;
  final Color color = new Color(255, 255, 255, 200);

  public InfoAmmo(SceneGame scene) {
    super(scene, 470.0D, 15.0D, 360.0D, 84.0D);
    this.sceneGame = scene;
  }

  public void drawEntity() {
    String ammo = "AMMO:" + this.sceneGame.thePlayer.getAmmo();
    float size = 32.0F;
    CERenderEngine.switchFont("Copperplate Gothic Bold");
    double len = CERenderEngine.getStringLength(ammo, 32.0F);
    CERenderEngine.drawString(799.0D - len, 130.0D, ammo, 32.0F, this.color);
  }
}