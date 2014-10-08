package cn.weathfold.demo.over;

import cn.weathfold.critengine.CritEngine;
import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.render.CERenderEngine;
import cn.weathfold.critengine.render.RenderUtils;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.util.Rect;
import org.lwjgl.opengl.GL11;

public class EntityInfo extends Entity
{
  public EntityInfo(Scene scene)
  {
    super(scene, 200.0D, 0.0D, 413.0D, 43.0D);
    setTexture("over_info");
  }

  public void drawEntity() {
    GL11.glPushMatrix();
    float alpha = 0.2F + 0.4F * (1.0F + (float)Math.sin(CritEngine.getVirtualTime() / 1000.0D));
    GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
    CERenderEngine.bindTexture(this.textureID);
    RenderUtils.renderTexturedQuads(getGeomProps(), 
      this.mapping.getMinX(), this.mapping.getMinY(), this.mapping.getMaxX(), this.mapping.getMaxY());
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
}