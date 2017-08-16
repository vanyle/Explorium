package com.vanyle.procedural;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.data.BlockData;
import com.vanyle.math.VMath;

public class TextureGenerator {
	
	
	public static void setup() {
		for(int i = 0;i < BlockData.blockType;i++) {
			for(int j = 0;j < BlockData.blockDiversity;j++)
				BlockData.texturemap[i][j] = blockIdToImage(i,16,16, Math.random()*10);
		}
	}
	
	// uses int[][] to represent image for optimization.
	public static BufferedImage blockIdToImage(int id,int w,int h,double seed){
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Color c;
		Color mainc = BlockData.toColor(id);
		
		double[] noiseData = BlockData.getNoiseData(id);
		double variX = noiseData[0];
		double variY = noiseData[1];
		double strength = noiseData[2];
		
		double smallVY = noiseData[3];
		double smallVX = noiseData[4];
		double weakStrength = noiseData[5];
		
		for(int i = 0;i < w;i++) {
			for(int j = 0;j < h;j++) {
				c = strength != 0 && weakStrength != 0 ? trick(mainc,turbulence(i+3.1f,j+3.1f,1,seed)*80) : mainc;
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
	private static double turbulence(double x, double y, double size,double seed){ // generate fractal noise
	  double value = 0.0, initialSize = size;
	  while(size >= 1){
	    value += VMath.noise(x / size, y / size, seed*3 - 0.1f) * size;
	    size /= 2.0;
	  }
	  if(value < 0.1) {
		  System.out.println(value/initialSize);
	  }
	  return value / initialSize;
	}
	private static double halton(int base, int index) {
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
	private static boolean regionFill(double[][] regions){
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
