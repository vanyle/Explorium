package com.vanyle.blocks;

import java.awt.image.BufferedImage;

public class BlockAir extends Block{

	public BlockAir(double seed) {
		super(seed);
		isCollide = false;
	}

	@Override
	public BufferedImage getTexture(double seed) {
		// TODO Auto-generated method stub
		return null;
	}


}
