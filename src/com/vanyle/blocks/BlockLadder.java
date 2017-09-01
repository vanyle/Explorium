package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockLadder extends Block{
	public BlockLadder(double seed) {
		super(seed);
		isCollide = false; // required if the collision is weird
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(133,94,66);
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.fade(TextureGenerator.trick(mainColor, -30),mainColor,Math.abs(VMath.mod(j,16)-16)/16);

				//strength != 0 && weakStrength != 0 ? trick(mainc,turbulence(i+3.1f,j+3.1f,1,seed)*80) : mainc;
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}

}
