package cn.weathfold.demo.menu;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import org.lwjgl.opengl.GL11;

public class EntityTitle extends Entity
{
  public static final int WIDTH = 256;
  public static final int HEIGHT = 72;

  public EntityTitle(SceneMenu scene)
  {
    super(scene, 0.0D, 440.0D, 256.0D, 72.0D);
    setTexture("title");
  }

  public void drawEntity()
  {
    CERenderEngine.bindTexture(this.textureID);
    GL11.glPushMatrix();
    float alpha = 0.2F + 0.4F * (1.0F + (float)Math.sin(CritEngine.getVirtualTime() / 1000.0D));
    GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
    RenderUtils.renderTexturedQuads(getGeomProps());
    GL11.glPopMatrix();
  }
}