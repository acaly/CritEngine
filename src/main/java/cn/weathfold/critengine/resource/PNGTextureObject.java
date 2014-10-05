/**
 * 
 */
package cn.weathfold.critengine.resource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import cn.weathfold.critengine.CritEngine;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * PNG贴图结构
 * @author WeAthFolD
 *
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
			
			buf = ByteBuffer.allocateDirect(4 * dec.getWidth() * dec.getHeight());
			
			dec.decode(buf, dec.getWidth() * 4, Format.RGBA);
			
			buf.flip();
			
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.render.TextureObject#getBuffer()
	 */
	@Override
	public ByteBuffer getBuffer() {
		return buf;
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.render.TextureObject#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see cn.weathfold.critengine.render.TextureObject#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

}
