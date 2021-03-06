package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockSand extends Block{
	public BlockSand(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(255,255,0);
		
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.fade(mainColor,TextureGenerator.trick(mainColor,-40),Math.abs(VMath.mod(i-j,32)-16)/16);
				c = TextureGenerator.trick(c,VMath.noise(i*13. + .1, j*13. + .1, seed + .1)*128);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
}
