package com.vanyle.render_utils;

public class Color {
	private int r = 0;
	private int g = 0;
	private int b = 0;
	private int a = 255;
	
	public static final String[] colorNames = {"white","black","green","red","yellow","orange","purple","pink","blue","gray","brown"};
	public static final int[][] colorModels = {{255,255,255},{0,0,0},{0,255,0},{255,0,0},
			{255,255,0},{255,128,0},{128,0,256},{256,0,256},{0,0,255},{128,128,128},{128,64,0}};
	
	public Color(int r,int g,int b){
		setR(r);
		setG(g);
		setB(b);
	}
	public Color(float r,float g,float b){
		setR((int)(r*256));
		setG((int)(g*256));
		setB((int)(b*256));
	}
	public Color(int r,int g,int b,int a){
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}
	public Color(float r,float g,float b,float a){
		setR((int)(r*256));
		setG((int)(g*256));
		setB((int)(b*256));
		setA((int)(a*256));
	}
	public static Color getColorFromHSV(int hue,int saturation,int value){
		int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return new Color(value, t, p);
	      case 1: return new Color(q, value, p);
	      case 2: return new Color(p, value, t);
	      case 3: return new Color(p, q, value);
	      case 4: return new Color(t, p, value);
	      case 5: return new Color(value, p, q);
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
	    }
	}
	public void plain_noise(int noise){
		
	}
	public int getR(){
		return r;
	}
	public int getG(){
		return g;
	}
	public int getB(){
		return b;
	}
	public int getA(){
		return a;
	}
	public float getFloatR(){
		return r/256f;
	}
	public float getFloatG(){
		return g/256f;
	}
	public float getFloatB(){
		return b/256f;
	}
	public float getFloatA(){
		return a/256f;
	}
	public void setR(int r){
		this.r = Math.max(Math.min(r,255),0);
	}
	public void setG(int g){
		this.g = Math.max(Math.min(g,255),0);
	}
	public void setB(int b){
		this.b = Math.max(Math.min(b,255),0);
	}
	public void setA(int a){
		this.a = Math.max(Math.min(a,255),0);
	}
	public double[] getHSV(){
		double computedH = 0;
		double computedS = 0;
		double computedV = 0;

		 double minRGB = Math.min(getFloatR(),Math.min(getFloatG(),getFloatB()));
		 double maxRGB = Math.max(getFloatR(),Math.max(getFloatG(),getFloatB()));

		 // Black-gray-white
		 if (minRGB==maxRGB) {
		  computedV = minRGB;
		  return new double[]{0,0,computedV};
		 }

		 // Colors other than black-gray-white:
		 double d = (r==minRGB) ? g-b : ((b==minRGB) ? r-g : b-r);
		 double h = (r==minRGB) ? 3 : ((b==minRGB) ? 1 : 5);
		 computedH = 60*(h - d/(maxRGB - minRGB));
		 computedS = (maxRGB - minRGB)/maxRGB;
		 computedV = maxRGB;
		 return new double[]{computedH,computedS,computedV};
	}
	
	public void setHSV(int h,int s,int v){
		Color c = getColorFromHSV(h,s,v);
		r = c.getR();
		g = c.getG();
		b = c.getB();
	}
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+"@"+this.hashCode()+"@ ("+r+";"+g+";"+b+")";
	}
	public static String getName(Color c){
		int minindex = 0;
		int minprox = 256*3;
		for(int i = 0;i < colorModels.length;i++){
			int[] cu = colorModels[i];
			int newprox = Math.abs(cu[0]-c.r) + Math.abs(cu[1]-c.g) + Math.abs(cu[2] + c.b);
			if(newprox < minprox){
				minprox = newprox;
				minindex = i;
			}
		}
		return colorNames[minindex];
	}
	public static Color getColorFromName(String s){
		for(int i = 0;i < colorModels.length;i++){
			if(colorNames[i].equals(s))
				return new Color(colorModels[i][0],colorModels[i][1],colorModels[i][2]);
		}
		return new Color(0,0,0);
	}
}
