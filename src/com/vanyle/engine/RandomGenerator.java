package com.vanyle.engine;

public class RandomGenerator{
  private double seed = 0.5;
  private long nextx = 512;
  private double previous = 0.5;
  private static final double[] coef = {13/12,Math.sqrt(2),7/6,Math.sqrt(5),5/7};
  
  public RandomGenerator(double seed){
    this.seed = Math.atan(seed);
    this.nextx = (long)((seed+1)*1000);
  }
  
  public double nextDouble(){
    int count = (int)(seed * 2.24 + 3 + previous * 3.63);
    for (int i = 0; i < count; i++) {
      previous = (previous * (1 - previous) * 3.9999);
    }
    return previous;
  }
  public double previousDouble(){
	  return previous;
  }
  public double next() {
	  long x = nextx;
	  x ^= (x << 21);
	  x ^= (x >>> 35);
	  x ^= (x << 4);
	  nextx = x;
	  x &= ((1L << 10) - 1);
	  return (double) x/1024f;
  }
  public static double curves(double x,double[] coef,double max,double proximity){
	  double r = 0;
	  double add = 0;
	  for(int i = 0;i < coef.length;i++){
		  add = (i%10)/9 * Math.PI/2;
		  r += Math.cos((x+add) * coef[i]/proximity);
	  }
	  r /= coef.length/max;
	  return r;
  }
  public static double pseudo1dPerlin(double x,double proximity){
	  return curves(x, coef, 1, proximity);
  }
  public static int IntegerCurve(double x,double proximity){
	  return (int)(x%(Math.cos(13*x/12)*proximity));
  }
}
