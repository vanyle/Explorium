package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockWater extends Block{
	public BlockWater(double seed) {
		super(seed);
		isCollide = false;
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(0,0,255);
		
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;

		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.trick(mainColor,VMath.noise(i*13. + .1, j*13. + .1, seed + .1)*128);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
}
