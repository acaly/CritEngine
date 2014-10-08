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

/**
 * 渲染引擎，接管贴图绑定和字体绘制等（教练：MD这也叫引擎？？？）
 * @author WeAthFolD
 */
public class CERenderEngine {
	
	private static TrueTypeFont currentFont = null;
	private static Map<String, TrueTypeFont> fontMap = new HashMap<String, TrueTypeFont>(); //已经预加载过的字体表
	private static int LOAD_FONT_SIZE = 32; //加载字体的像素大小
	private static Field fieldFontTexture; //……反射大法好

	static {
		try {
			fieldFontTexture = TrueTypeFont.class.getDeclaredField("fontTexture");
			fieldFontTexture.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public static void bindTexture(String key) {
		int id = CEResourceHandler.queryTextureId(key);
		if (id != 0) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		} else {
			CEDebugger.error("Attempting to bind a NULL texture named " + key);
		}
	}

	public static void switchFont(Font font) {
		TrueTypeFont font2 = new TrueTypeFont(font, false);
		fontMap.put(font.getFontName(), font2);
		currentFont = font2;
	}

	public static void setLoadFontSize(int f) {
		LOAD_FONT_SIZE = f;
	}

	/**
	 * 切换到某个字体。请在绘制字体前先调用她~
	 */
	public static void switchFont(String fontName) {
		TrueTypeFont font = preloadFont(fontName);
		currentFont = font;
	}

	/**
	 * 预加载字体
	 * @param fontName 字体名
	 */
	public static TrueTypeFont preloadFont(String fontName) {
		TrueTypeFont font = fontMap.get(fontName);
		if (font == null) {
			font = new TrueTypeFont(new Font(fontName, 0, LOAD_FONT_SIZE), false);
			fontMap.put(fontName, font);
		}
		return font;
	}
	
	/**
	 * 获取字符串的长度（注意先绑定字体）
	 */
	public static double getStringLength(String s) {
		return getStringLength(s, LOAD_FONT_SIZE);
	}

	/**
	 * 获取字符串的长度（注意先绑定字体）
	 */
	public static double getStringLength(String s, float size) {
		return currentFont.getWidth(s) * size / LOAD_FONT_SIZE;
	}
	
	/**
	 * 获取当前字体的高度
	 */
	public static double getStringHeight() {
		return getStringHeight(LOAD_FONT_SIZE);
	}
	
	/**
	 * 获取当前字体高度
	 */
	public static double getStringHeight(float size) {
		return currentFont.getHeight() * size / LOAD_FONT_SIZE;
	}

	/**
	 * 绘制字符串
	 * @param x
	 * @param y
	 * @param s 字符串
	 * @param size 字大小
	 */
	public static void drawString(double x, double y, String s, float size) {
		drawString(x, y, s, size, Color.white);
	}

	/**
	 * 绘制字符串
	 * @param x
	 * @param y
	 * @param s 字串内容
	 * @param size 字大小
	 * @param color 颜色
	 */
	public static void drawString(double x, double y, String s, float size,
			Color color) {
		try {
			Texture tex = (Texture) fieldFontTexture.get(currentFont);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		float scale = size / LOAD_FONT_SIZE;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0.0D);
		GL11.glScalef(scale, -scale, scale);
		currentFont.drawString(0.0F, 0.0F, s, color);
		GL11.glPopMatrix();
	}

	/**
	 * 绘制字符串
	 * @param x
	 * @param y
	 * @param s 字串内容
	 */
	public static void drawString(double x, double y, String s) {
		drawString(x, y, s, LOAD_FONT_SIZE);
	}
}