package com.vanyle.data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 
 * @author vanyle
 *
 * Converts blockids into colors
 */
public class BlockData {
	
	public static final int ID_AIR = 0;
	public static final int ID_STONE = 1;
	public static final int ID_GRASS = 2;
	public static final int ID_SAND = 3;
	public static final int ID_WATER = 4;
	public static final int ID_DARKBRICK = 5;
	public static final int ID_CLOUD = 6;
	public static final int ID_DIRT = 7;
	public static final int ID_TIMEGOO = 8;
	public static final int ID_TRUNK = 9;
	public static final int ID_LEAF = 10; // a
	public static final int ID_EMPTYNESS = 11; // b
	public static final int ID_LADDER = 12;  // c
	public static final int ID_DOOR = 13; // d
	
	private final static Color[] map = {
			new Color(0,0,0,0), // air
			new Color(200,200,200), // stone
			new Color(0,255,0), // grass
			new Color(255,255,0), // sand
			new Color(0,0,255), // water
			new Color(100,100,100), // darkbrick
			new Color(230,230,230,200), // cloud
			new Color(139,69,19), // dirt
			new Color(139,0,139), // time goo
			new Color(164, 72, 1), // trunk
			new Color(58, 95, 11), // leaf
			new Color(0,0,0), // nothing (vantablack)
			new Color(133,94,66), // ladder
			new Color(133,94,66) // door
	};
	private final static double[][] noisemap = {
			{0,0,0 , 0,0,0},
			{100,100,20 , 20,20,20}, // noiseX
			{0,0,0 , 0,0,0},
			{100,100,20 , 10,10,10}
	};
	public static final int blockType = map.length;
	public static final int blockDiversity = 10;
	
	public static BufferedImage[][] texturemap = new BufferedImage[blockType][blockDiversity];
	
	public static BufferedImage toTexture(int id,int x,int y) {
		// retreive Texture Array
		BufferedImage[] biArray = texturemap[id];
		long lx = (long)x;
		long ly = (long)y;
		Random r = new Random(lx+ly*(long)Math.pow(2, 32));
		long res = r.nextInt()%blockDiversity;
		res = res < 0 ? res+blockDiversity : res;
		return biArray[(int)res]; 
	}
	
	public static Color toColor(int id) {
		try {
			return map[id];
		}catch(ArrayIndexOutOfBoundsException e) {
			return Color.BLACK;
		}
				//return new Color(Math.min(255,Math.max(id+128,0)),0,0); // useful for debug
	}
	public static double[] getNoiseData(int id) {
		try {
			return noisemap[id];
		}catch(ArrayIndexOutOfBoundsException e) {
			double[] d = {0,0,0 , 0,0,0};
			return d;
		}
	}
}
