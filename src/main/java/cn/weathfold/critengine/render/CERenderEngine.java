/**
 * 
 */
package cn.weathfold.critengine.render;

import java.awt.Font;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import cn.weathfold.critengine.resource.CEResourceHandler;

/**
 * @author WeAthFolD
 *
 */
public class CERenderEngine {
	
	private static TrueTypeFont currentFont = null;
	//现在用的slick util, 包装为了以后到C++的转换
	private static Map<String, TrueTypeFont> fontMap = new HashMap<String, TrueTypeFont>();
	private static int DEFAULT_FONT_SIZE = 32;
	
	private static Field fieldFontTexture;
	static {
		try {
			fieldFontTexture = TrueTypeFont.class.getDeclaredField("fontTexture");
			fieldFontTexture.setAccessible(true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void bindTexture(String key) {
		int id = CEResourceHandler.queryTextureId(key);
		if(id != 0)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	public static void switchFont(Font font) {
		TrueTypeFont font2 = new TrueTypeFont(font, false);
		fontMap.put(font.getFontName(), font2);
		currentFont = font2;
	}
	
	public static void switchFont(String fontName) {
		TrueTypeFont font = fontMap.get(fontName);
		if(font == null) {
			font = new TrueTypeFont(new Font(fontName, Font.PLAIN, DEFAULT_FONT_SIZE), false);
			fontMap.put(fontName, font);
		}
		currentFont = font;
	}
	
	public static void drawString(float x, float y, String s, float size) {
		try {
			Texture tex = (Texture) fieldFontTexture.get(currentFont);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		} catch(Exception e) {
			e.printStackTrace();
		}
		float scale = size / DEFAULT_FONT_SIZE;
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, -scale, scale);
		currentFont.drawString(0, 0, s, Color.white);
		GL11.glPopMatrix();
	}
	
	public static void drawString(float x, float y, String s) {
		drawString(x, y, s, DEFAULT_FONT_SIZE);
	}
	
}
