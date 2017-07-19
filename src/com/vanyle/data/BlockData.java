package com.vanyle.data;

import java.awt.Color;

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
	public static final int ID_LEAF = 10;
	
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
			new Color(58, 95, 11) // leaf
	};
	
	public static Color toColor(int id) {
		try {
			return map[id];
		}catch(ArrayIndexOutOfBoundsException e) {
			return Color.BLACK;
		}
				//return new Color(Math.min(255,Math.max(id+128,0)),0,0); // useful for debug
	}
}
