package com.vanyle.menu;

import java.awt.Graphics;

import com.vanyle.data.FontData;
import com.vanyle.graphics.Window;

public abstract class Widget {
	int x;
	int y;
	protected int width;
	protected int height;
	protected String text;
	private int size;
	public int padding = 20;
	
	public void setPadding(int padding) {
		this.padding = padding;
		width = FontData.length(text, size)+padding*2;
		height = size*6+padding*2;
	}
	public void setSize(int size) {
		this.size = size;
		width = FontData.length(text, size)+padding*2;
		height = size*6+padding*2;
	}
	public void setText(String text) {
		this.text = text;
		width = FontData.length(text, size)+padding*2;
	}
	public String getText() {
		return text;
	}
	public int getSize() {
		return size;
	}
	public boolean in(int x,int y) {
		return this.x-padding < x && this.y-padding < y && this.y+height-padding > y && this.x+width-padding > x;
	}
	public void centerX() {
		x = Window.WIDTH/2 - FontData.length(text, size)/2;
	}
	public abstract void draw(Graphics g);
}
