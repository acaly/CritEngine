package cn.weathfold.demo.game.obstacle;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.demo.game.SceneGame;

public class ObstacleTemplate
  implements IEntityTemplate
{
  public final double width;
  public final double height;
  public final double zHeight;
  public final int dps;
  public final String texture;
  public final boolean doesCollide;
  public final SceneGame scene;
  public double offsetX;
  public double offsetY;
  public double drawWidth;
  public double drawHeight;

  public ObstacleTemplate(SceneGame scene, double w, double h, double zHeight, int dps, String texture, boolean collide)
  {
    this.drawWidth = (this.width = w);
    this.drawHeight = (this.height = h);
    this.scene = scene;
    this.zHeight = zHeight;
    this.dps = dps;
    this.texture = texture;
    this.doesCollide = collide;
  }

  public ObstacleTemplate setRenderProps(float x, float y, float dw, float dh) {
    this.offsetX = x;
    this.offsetY = y;
    this.drawWidth = dw;
    this.drawHeight = dh;
    return this;
  }

  public void generate(double x, double y) {
    this.scene.spawnEntity(new EntityObstacle(this.scene, x, y, this.width, this.height, this.texture, 
      this.zHeight, this.dps, this.doesCollide, this.offsetX, this.offsetY, this.drawWidth, this.drawHeight));
  }
}