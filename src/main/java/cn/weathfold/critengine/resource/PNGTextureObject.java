package cn.weathfold.critengine.resource;

import java.io.InputStream;
import java.nio.ByteBuffer;

import cn.weathfold.critengine.CritEngine;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * PNG贴图加载器
 * @author WeAthFolD
 */
public class PNGTextureObject implements TextureObject {

	ByteBuffer buf;
	int width, height;

	public PNGTextureObject(String loc) {
		try {
			InputStream in = CritEngine.class.getResourceAsStream(loc);
			PNGDecoder dec = new PNGDecoder(in);

			width = dec.getWidth();
			height = dec.getHeight();

			buf = ByteBuffer.allocateDirect(4 * dec.getWidth()
					* dec.getHeight());

			dec.decode(buf, dec.getWidth() * 4, Format.RGBA);

			buf.flip();

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ByteBuffer getBuffer() {
		return buf;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * 读取一个PNG序列
	 * @param arr 资源地址数组
	 * @return
	 */
	public static PNGTextureObject[] readArray(String... arr) {
		PNGTextureObject res[] = new PNGTextureObject[arr.length];
		for (int i = 0; i < arr.length; ++i) {
			res[i] = new PNGTextureObject(arr[i]);
		}
		return res;
	}

}
