package cn.weathfold.critengine.render;

import cn.weathfold.critengine.CEDebugger;
import cn.weathfold.critengine.resource.CEResourceHandler;
import java.awt.Font;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class CERenderEngine
{
  private static TrueTypeFont currentFont = null;

  private static Map<String, TrueTypeFont> fontMap = new HashMap();
  private static int DEFAULT_FONT_SIZE = 32;
  private static Field fieldFontTexture;

  static
  {
    try
    {
      fieldFontTexture = TrueTypeFont.class.getDeclaredField("fontTexture");
      fieldFontTexture.setAccessible(true);
    }
    catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    catch (SecurityException e) {
      e.printStackTrace();
    }
  }

  public static void bindTexture(String key) {
    int id = CEResourceHandler.queryTextureId(key);
    if (id != 0)
      GL11.glBindTexture(3553, id);
    else
      CEDebugger.error("Attempting to bind a NULL texture named " + key);
  }

  public static void switchFont(Font font) {
    TrueTypeFont font2 = new TrueTypeFont(font, false);
    fontMap.put(font.getFontName(), font2);
    currentFont = font2;
  }

  public static void setDefaultFontSize(int f) {
    DEFAULT_FONT_SIZE = f;
  }

  public static void switchFont(String fontName) {
    TrueTypeFont font = preloadFont(fontName);
    currentFont = font;
  }

  public static TrueTypeFont preloadFont(String fontName) {
    TrueTypeFont font = (TrueTypeFont)fontMap.get(fontName);
    if (font == null) {
      font = new TrueTypeFont(new Font(fontName, 0, DEFAULT_FONT_SIZE), false);
      fontMap.put(fontName, font);
    }
    return font;
  }

  public static double getStringLength(String s) {
    return getStringLength(s, DEFAULT_FONT_SIZE);
  }

  public static double getStringLength(String s, float size) {
    return currentFont.getWidth(s) * size / DEFAULT_FONT_SIZE;
  }

  public static void drawString(double x, double y, String s, float size) {
    drawString(x, y, s, size, Color.white);
  }

  public static void drawString(double x, double y, String s, float size, Color color) {
    try {
      Texture tex = (Texture)fieldFontTexture.get(currentFont);
      GL11.glBindTexture(3553, tex.getTextureID());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    float scale = size / DEFAULT_FONT_SIZE;
    GL11.glPushMatrix();
    GL11.glTranslated(x, y, 0.0D);
    GL11.glScalef(scale, -scale, scale);
    currentFont.drawString(0.0F, 0.0F, s, color);
    GL11.glPopMatrix();
  }

  public static void drawString(double x, double y, String s) {
    drawString(x, y, s, DEFAULT_FONT_SIZE);
  }
}