/**
 * 
 */
package cn.weathfold.critengine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.physics.CEPhysicEngine;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;

public class CEUpdateProcessor
{
  private static Scene activeScene;
  private static Set<IEntityProcessor> entityProcessors = new HashSet();
  protected static boolean tickState = true;

  static {
    entityProcessors.add(CEPhysicEngine.INSTANCE);
  }

  public static void frameUpdate(Scene scene) {
    activeScene = scene;

    CESoundEngine.frameUpdate();

    activeScene.frameUpdate();
    if (!tickState) {
      tickState = true;
      return;
    }

    List<Entity> list = activeScene.getSceneEntities();
    manualUpdateEntities(list, activeScene);

    drawCall();
  }

  public static void manualUpdateEntities(Scene scene) {
    manualUpdateEntities(scene.getSceneEntities(), scene);
  }

  public static void manualUpdateEntities(List<Entity> list, Scene scene)
  {
    for (int i = 0; i < list.size(); i++) {
      Entity e = (Entity)list.get(i);
      if (scene.doesUpdate(e))
      {
        if (e.deathFlag) {
          i--;
          list.remove(e);
        }
        else
        {
          e.onFrameUpdate();
          for (IEntityProcessor prc : entityProcessors) {
            prc.processEntity(e);
          }
          if (!tickState) {
            tickState = true;
            return; } 
        }
      }
    }
  }

  private static void drawCall() { GL11.glClear(16640);
    GL13.glActiveTexture(33984);
    GL11.glEnable(3553);

    GL11.glPushMatrix();
    activeScene.renderBackground();
    activeScene.renderForeground();
    GL11.glPopMatrix();
  }
}
