package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockCloud extends Block{
	public BlockCloud(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(230,230,230,200);
		
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.trick(mainColor,
						VMath.noise(i/20. -.1,j/20. - .5,seed) * 20 -
						VMath.noise(i/10. -.1,j/10. - .5,seed) * 10
				);

				//strength != 0 && weakStrength != 0 ? trick(mainc,turbulence(i+3.1f,j+3.1f,1,seed)*80) : mainc;
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
}
