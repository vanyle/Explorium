package com.vanyle.blocks;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Block {
	
	public boolean isGravity = false;
	public boolean isCollide = true;
	public boolean canSwimThrough = false;
	public int Viscosity = 1; // 0 = air 1 = stone
	
	public Color mainColor = Color.GRAY;
	public Color secondaryColor = Color.BLACK;
	
	public BufferedImage texture;
	
	public Block(double seed) {
		texture = getTexture(seed); // init texture
	}
	
	/**
	 * Returns a 1024*1024 texture of the block proceduraly generated based on the seed
	 * @param seed
	 * @return
	 */
	public abstract BufferedImage getTexture(double seed);
}
