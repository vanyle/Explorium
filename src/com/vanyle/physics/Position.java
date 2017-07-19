package com.vanyle.physics;

import com.vanyle.math.VMath;

/**
 *  Represent the position of a block in the world
 * @author vanyle
 *
 */
public class Position {
	
	private static final double CSIZE = Chunk.CSIZE;
	
	public double cx = 0; // chunk position, usually an integer
	public double cy = 0;
	public double x = 0; // position inside the chunk
	public double y = 0;
	
	public Position(double x,double y,int cx,int cy) {
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
		overflow();
	}
	public void moveX(double mx) {
		this.x += mx;
		cx += Math.floor(this.x/CSIZE);
		x = VMath.mod(x,CSIZE);
	}
	public void moveY(double my) {
		this.y += my;
		cy += Math.floor(y/CSIZE);
		y = VMath.mod(y,CSIZE);
	}
	public void add(Position p) {
		cx += p.cx;
		cy += p.cy;
		x += p.x;
		y += p.y;
		overflow();
	}
	public void substract(Position p) {
		cx -= p.cx;
		cy -= p.cy;
		x -= p.x;
		y -= p.y;
		overflow();
	}
	public void multiply(double d) {
		cx *= d;
		cy *= d;
		x *= d;
		y *= d;
		overflow();
	}
	public void overflow() {
		cx += Math.floor(x/CSIZE);
		cy += Math.floor(y/CSIZE);
		x = VMath.mod(x,CSIZE);
		y = VMath.mod(y,CSIZE);
	}
	public Position clone() {
		return new Position(x,y,getCX(),getCY());
	}
	public void round() {
		x = Math.round(x);
		y = Math.round(y);
		cx = Math.floor(cx);
		cy = Math.floor(cy);
	}
	public double x() {
		return cx*CSIZE + x;
	}
	public double y() {
		return cy*CSIZE + y;
	}
	public int getX() {
		return (int)Math.floor(x);
	}
	public int getY() {
		return (int)Math.floor(y);
	}
	public int getCX() {
		return (int)Math.floor(cx);
	}
	public int getCY() {
		return (int)Math.floor(cy);
	}
	public double dist(Position p) { // compute the square of the distance
		return (p.x()-x()) * (p.x() - x()) + (p.y()-y()) * (p.y()-y());
	}
	public void converge(Position b,double speed,double driftx,double drifty) {
		x = (b.x() - x() - driftx) * speed + x();
		y = (b.y() - y() - drifty) * speed + y();
		cx = 0;
		cy = 0;
		overflow();
	}
	public String toString() {
		return "("+VMath.format(x(),2)+";"+VMath.format(y(),2)+")";
	}
}
