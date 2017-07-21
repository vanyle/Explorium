package com.vanyle.procedural;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.data.BlockData;
import com.vanyle.math.VMath;

public class TextureGenerator {
	// uses int[][] to represent image for optimization.
	public static BufferedImage blockIdToImage(int id,int w,int h){
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		Color mainc = BlockData.toColor(id);
		
		for(int i = 0;i < w;i++) {
			for(int j = 0;j < h;j++) {
				c = trick(mainc, VMath.noise(i/20F, j*1.2, 4.3)*50 );
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
	public static Color trick(Color c,double tr) {
		int r = (int)Math.max(Math.min(c.getRed()+tr,255),0);
		int g = (int)Math.max(Math.min(c.getGreen()+tr,255),0);
		int b = (int)Math.max(Math.min(c.getBlue()+tr,255),0);
		return new Color(r,g,b);
	}
}
