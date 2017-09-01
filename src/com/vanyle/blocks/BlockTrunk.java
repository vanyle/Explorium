package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockTrunk extends Block {

	public BlockTrunk(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(164,72,1);
		
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.fade(mainColor,TextureGenerator.trick(mainColor,-60),Math.abs(VMath.mod(i-j,8)-4)/8);
				c = TextureGenerator.trick(c, VMath.noise(i, j/10., seed) * 60);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}

}
