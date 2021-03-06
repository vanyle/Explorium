package com.vanyle.math;

import java.util.Random;

public class VMath {
	public static int pow(int a,int b){
		int r = 1;
		for(int i = 0;i < b;i++){
			r *= a;
		}
		return r;
	}
	public static int fact(int a){
		int r = 1;
		for(int i = 1;i <= a;a++){
			r *=i;
		}
		return r;
	}
	public static double square(double a) {
		return a * a;
	}
	public static double dist(double x1,double y1,double x2,double y2) {
		return square(x1-x2) + square(y1-y2);
	}
	public static double mod(double a,double b) {
		a = a%b;
		if(a<0)a+= b;
		return a;
	}
	public static boolean contains(double x,double y,double x1,double x2,double y1,double y2){
		return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
	}
	/**
	 * Associate with each group of coordinates x,y,z random number between 0 and 1
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static double numerate(int x,int y,int z){
		Random r = new Random((long)(noise(x*1000.1 + 0.1,y*100.2 + 0.1,z*100.3 + 0.1)*100000));
		return r.nextDouble();
	}
	public static double sign(double s) {
		if(s == 0)
			return 0;
		if(s > 0)
			return 1;
		return -1;
	}
	public static double noise(double x, double y, double z){
	    int X = (int)Math.floor(x) & 0xFF;
	    int Y = (int)Math.floor(y) & 0xFF;
	    int Z = (int)Math.floor(z) & 0xFF;
	    x -= Math.floor(x);
	    y -= Math.floor(y);
	    z -= Math.floor(z);
	    double u = fade(x);
	    double v = fade(y);
	    double w = fade(z);
	    int A = p[X] + Y;int AA = p[A] + Z;int AB = p[(A + 1)] + Z;
	    int B = p[(X + 1)] + Y;int BA = p[B] + Z;int BB = p[(B + 1)] + Z;
	    
	    return lerp(w, lerp(v, lerp(u, grad(p[AA], x, y, z), 
	      grad(p[BA], x - 1.0D, y, z)), 
	      lerp(u, grad(p[AB], x, y - 1.0D, z), 
	      grad(p[BB], x - 1.0D, y - 1.0D, z))), 
	      lerp(v, lerp(u, grad(p[(AA + 1)], x, y, z - 1.0D), 
	      grad(p[(BA + 1)], x - 1.0D, y, z - 1.0D)), 
	      lerp(u, grad(p[(AB + 1)], x, y - 1.0D, z - 1.0D), 
	      grad(p[(BB + 1)], x - 1.0D, y - 1.0D, z - 1.0D))));
	  }
	  public static String format(double d,int i) {
		  double keep = Math.pow(10, i);
		  return String.valueOf((Math.round(d*keep)/keep)).trim();
	  }
	  static double fade(double t){return t * t * t * (t * (t * 6.0D - 15.0D) + 10.0D);} 
	  static double lerp(double t, double a, double b){return a + t * (b - a);} 
	  static double grad(int hash, double x, double y, double z){
	    int h = hash & 0xF;
	    double u = h < 8 ? x : y;
	    double v = (h == 12) || (h == 14) ? x : h < 4 ? y : z;
	    return ((h & 0x1) == 0 ? u : -u) + ((h & 0x2) == 0 ? v : -v);
	  }
	  static final int[] p = new int[512];
	  static final int[] permutation = { 151, 160, 137, 91, 90, 15, 
	    131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 
	    190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 
	    88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 
	    77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 
	    102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 
	    135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 
	    5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 
	    223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 
	    129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 
	    251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 
	    49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 
	    138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
	  static{
	    for (int i = 0; i < 256; i++) {
	      p[(256 + i)] = (p[i] = permutation[i]);
	    }
	  }
}
