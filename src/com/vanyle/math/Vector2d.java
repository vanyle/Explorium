package com.vanyle.math;

public class Vector2d {
	private double x = 0;
	private double y = 0;
	
	public Vector2d(){}
	public Vector2d(double x,double y){
		this.x = x;
		this.y = y;
	}
	public double x(){
		return x;
	}
	public double y(){
		return y;
	}
	public void x(double x){
		this.x = x;
	}
	public void y(double y){
		this.y = y;
	}
	public Vector2d add(Vector2d b){
		x += b.x;
		y += b.y;
		return this;
	}
	public Vector2d floor(){
		x = Math.floor(x);
		y = Math.floor(y);
		return this;
	}
	public Vector2d multiply(double a){
		x *= a;
		y *= a;
		return this;
	}
	public Vector2d apply(Vector2d v,boolean alert){
		Vector2d n = v.copy().normalize();
		Vector2d c = n.copy().rotate(90);
		// express v as a sum of n and c
		double[] express = express(n,c,alert);
		this.is(n.multiply(express[0]/v.mag()).add(c.multiply(express[1])));
		return this;
	}
	public double[] express(Vector2d a,Vector2d b,boolean alert){
		
		if(!collinear(a, b)){
			double ca = (y - (x*b.y)/b.x)/((b.x-a.x)*(b.y/b.x));
			double cb = copy().add(a.copy().multiply(-ca)).x / b.x;
			return new double[]{ca,cb};
		}
		if(alert)
			System.err.println(a+" and "+b+" are colinear !");
		return new double[]{0,0};
	}
	public boolean collinear(Vector2d a,Vector2d b){
		return (a.x*b.y == b.x*a.y);
	}
	public double dist(Vector2d a){
		return java.lang.Math.sqrt((a.x-x)*(a.x-x) + (a.y-y)*(a.y-y));
	}
	public double mag(){
		return java.lang.Math.sqrt(x*x+y*y);
	}
	public double rmag(){
		return x*x+y*y;
	}
	public Vector2d mag(double l){
		if(mag() != 0)
			return multiply(l/mag());
		return this;
	}
	public Vector2d normalize(){
		return mag(1);
	}
	public double heading(){
		return java.lang.Math.atan(x/y)*180/3.14159265358979;
	}
	public Vector2d rotate(int angle){
		double d = mag();
		x = Math.tan(angle*Math.PI/180);
		y = 1;
		mag(d);
		return this;
	}
	public boolean contains(Vector2d a,Vector2d b){
		return a.x < x && a.y < y && b.x > x && b.y > y;
	}
	public Vector2d matrix(double[][] m){
		x = m[0][0] * x + m[0][1] * y + m[0][2];
		y = m[1][0] * x + m[1][1] * y + m[1][2];
		
		return this;
	}
	public int select(int[][] m){
		return m[(int)x][(int)y];
	}
	public Vector2d copy(){
		return new Vector2d(x, y);
	}
	public void is(Vector2d v){
		x = v.x;
		y = v.y;
	}
	@Override
	public String toString(){
		return "("+x+";"+y+")";
	}
}
