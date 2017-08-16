package com.vanyle.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.vanyle.data.FontData;
import com.vanyle.graphics.Window;

public class Button {
	int x;
	int y;
	private String text;
	private int size;
	private int padding = 20;
	BufferedImage bg = RenderMenu.buttonbg;
	private int width;
	private int height;
	
	public Button(String text,int x,int y,int size) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.size = size;
		width = FontData.length(text, size)+padding*2;
		height = size*6+padding*2;
	}
	public void setBG(BufferedImage bg) {
		this.bg = bg;
	}
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
	public boolean inButton(int x,int y) {
		return this.x < x && this.y < y && this.y+height > y && this.x+width > x;
	}
	public void centerX() {
		x = Window.WIDTH/2 - FontData.length(text, size)/2;
	}
	public void draw(Graphics g) {
		g.drawImage(bg,x-padding,y-padding,width,height, null);
		FontData.drawString(text, x, y, g, size);
	}
}
