package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.procedural.TextureGenerator;

public class BlockVoid extends Block {

	public BlockVoid(double seed) {
		super(seed);
	}

	@Override
	public BufferedImage getTexture(double seed) { // get Texture is called before constructor
		mainColor = new Color(0,0,0);
		BufferedImage bi = new BufferedImage(TextureGenerator.TEX_W, TextureGenerator.TEX_H, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				bi.setRGB(i, j, mainColor.getRGB());
			}
		}
		return bi;
	}

}
