package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockDarkBrick extends Block{
	public BlockDarkBrick(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(50,50,50);
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = (i/5 + j/5) % 2 == 0 ? mainColor : TextureGenerator.trick(mainColor, 40);
				c = TextureGenerator.trick(c, VMath.noise(i/5, j/5, seed + 65.4f) * 40);
				
				//strength != 0 && weakStrength != 0 ? trick(mainc,turbulence(i+3.1f,j+3.1f,1,seed)*80) : mainc;
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
}
