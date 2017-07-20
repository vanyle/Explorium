package com.vanyle.math;

public class Vector3d {
	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	public Vector3d(){}
	public Vector3d(double x,double y,double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}
	public Vector3d add(Vector3d b){
		x += b.x;
		y += b.y;
		z += b.z;
		return this;
	}
	public Vector3d multiply(double a){
		x *= a;
		y *= a;
		z *= a;
		return this;
	}
	public double dist(Vector3d a){
		return java.lang.Math.sqrt((a.x-x)*(a.x-x) + (a.y-y)*(a.y-y) + (a.z-z)*(a.z-z));
	}
	public double mag(){
		return java.lang.Math.sqrt(x*x+y*y+z*z);
	}
	public double rmag(){
		return x*x+y*y+z*z;
	}
	public Vector3d mag(double l){
		return multiply(l/mag());
	}
	public Vector3d normalize(){
		return mag(1);
	}
	public double heading(){
		return java.lang.Math.atan(java.lang.Math.sqrt(x*x+z*z)/y)*180/3.14159265358979;
	}
	public Vector3d matrix(double[][] m){
		x = m[0][0] * x + m[0][1] * y + m[0][2] * z + m[0][3];
		y = m[1][0] * x + m[1][1] * y + m[1][2] * z + m[1][3];
		z = m[2][0] * x + m[2][1] * y + m[2][2] * z + m[2][3];
		
		return this;
	}
	public int select(int[][][] m){
		return m[(int)x][(int)y][(int)z];
	}
	public Vector3d copy(){
		return new Vector3d(x, y, z);
	}
	public void is(Vector3d v){
		x = v.x;
		y = v.y;
		z = v.z;
	}
	@Override
	public String toString(){
		return "("+x+";"+y+";"+z+")";
	}
}
