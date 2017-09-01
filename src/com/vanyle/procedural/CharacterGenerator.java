package com.vanyle.procedural;

import java.awt.image.BufferedImage;

import com.vanyle.life.Entity;

public class CharacterGenerator {
	
	public static int RES_W = 32;
	public static int RES_H = 32;
	
	public static BufferedImage characterTexture(Entity e,double seed) {
		return e.generateTexture(seed);
	}
}
