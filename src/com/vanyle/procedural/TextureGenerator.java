package com.vanyle.procedural;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.blocks.Blocks;
import com.vanyle.math.VMath;

public class TextureGenerator {
	
	public static final int TEX_W = 256;
	public static final int TEX_H = 256;
	
	// TODO uses int[][] to represent image for optimization.
	public static BufferedImage blockIdToImage(int id,double seed){
		return Blocks.block(id).b().getTexture(seed);
	}
	public static Color fade(Color a,Color b,double dist) { // dist=0 => a, dist=1 => b 
		return new Color(
				(int)(a.getRed() + (b.getRed() - a.getRed()) * dist),
				(int)(a.getGreen() + (b.getGreen() - a.getGreen()) * dist),
				(int)(a.getBlue() + (b.getBlue() - a.getBlue()) * dist));
	}
	public static BufferedImage generateButtonTexture(Color a,int w,int h,double seed) {
		BufferedImage bi = new BufferedImage(w,h, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = TextureGenerator.trick(a,VMath.noise(i/12. + .1, j/2. + .1, seed + .1)*60);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
	public static Color trick(Color c,double tr) {
		int r = (int)Math.max(Math.min(c.getRed()+tr,255),0);
		int g = (int)Math.max(Math.min(c.getGreen()+tr,255),0);
		int b = (int)Math.max(Math.min(c.getBlue()+tr,255),0);
		return new Color(r,g,b);
	}
	public static double turbulence(double x, double y, double size,double seed){ // generate fractal noise
	  double value = 0.0, initialSize = size;
	  while(size >= 1){
	    value += VMath.noise(x / size, y / size, seed*3 - 0.1f) * size;
	    size /= 2.0;
	  }
	  return value / initialSize;
	}
	public static double halton(int base, int index) {
		double f = 1.0;
		double r = 0.0;
		while(index > 0){
			f = f/base;
			r = r + f * (index % base);
			index = (int)Math.floor(index/base);
		}
		return r;
	}
	/**
	 * @param regions
	 * @return True then the map is ready
	 */
	public static boolean regionFill(double[][] regions){
		boolean f = false;
		for(int i = 0;i < regions.length;i++) {
			for(int j = 0;j < regions[i].length;j++) {
				if(regions[i][j] == 0) {
					regions[j][i] = neighbors(regions,i,j);
					f = true;
				}
			}
		}
		return f;
	}
	private static double neighbors(double[][] array,int x,int y) {
		int[] countvalues = new int[9];
		double[] countmap = new double[9];
		int l = 0;
		boolean f = false;
		
		for(int i = -1; i <= 1;i++) {
			for(int j = -1; j <= 1;j++) {
				try {
					f = false;
					for(int k = 0;k < l;k++) {
						if(countmap[k] == array[i+x][j+k]) {
							countvalues[k] ++;
							f = true;
						}
					}
					if(!f) {
						countmap[l] = array[i+x][j+y];
						countvalues[l] = 1;
						l++;
					}
				}catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
		int max = 0;
		double id = 0;
		// find the most common 
		for(int i = 0;i < l;i++) {
			if(countvalues[i] > max) {
				max = countvalues[i];
				id = countmap[i];
			}
		}
		return id;
	}
}
