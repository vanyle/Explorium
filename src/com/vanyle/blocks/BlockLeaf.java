package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockLeaf extends Block {

	public BlockLeaf(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(58,95,11);
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.fade(
						mainColor,
						TextureGenerator.trick(mainColor, 10),
						VMath.mod(i+j + VMath.noise(i*5. + .1, j*5. + .1, seed)*20,32)/16 - VMath.noise(i/10f, j/10f, seed));

				//strength != 0 && weakStrength != 0 ? trick(mainc,turbulence(i+3.1f,j+3.1f,1,seed)*80) : mainc;
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}

}
