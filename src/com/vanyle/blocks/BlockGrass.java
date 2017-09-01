package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.VMath;
import com.vanyle.procedural.TextureGenerator;

public class BlockGrass extends Block{
	
	public BlockGrass(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) {
		mainColor = new Color(0,255,0);
		secondaryColor = new Color(139,69,19);
		
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.fade(secondaryColor,TextureGenerator.trick(secondaryColor,-40),Math.abs(VMath.mod(i-j,32)-16)/16);
				if(VMath.mod(j,16) - 0 < Math.abs(VMath.noise(seed + .1,i/3.,seed + .2)*20)+1)
					c = TextureGenerator.trick(mainColor, Math.signum(VMath.noise(i*1.3, j*1.3, seed + .1)) * 40 - 30 );
				
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
}
