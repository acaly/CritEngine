package cn.weathfold.demo.game.enemy;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.critengine.scene.Scene;

public class TemplateTraceur
  implements IEntityTemplate
{
  Scene theScene;

  public TemplateTraceur(Scene sc)
  {
    this.theScene = sc;
  }

  public void generate(double x, double y)
  {
    this.theScene.spawnEntity(new EntityTraceur(this.theScene, x, y));
  }
}