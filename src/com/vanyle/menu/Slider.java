package com.vanyle.menu;

import java.awt.Color;
import java.awt.Graphics;

import com.vanyle.data.FontData;

public class Slider extends Widget{

	private double value = 0;
	private double multiplierDisplay = 100;
	
	public Slider(int x,int y,int width,int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.padding = 0;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = Math.max(Math.min(value,1),0);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		g.fillRect((int)(x+value * width),y-height/2,10,height*2);
		g.setColor(Color.WHITE);
		FontData.drawString((int)Math.floor(value*multiplierDisplay)+"",x+20,y+5,g,(height-10)/6);
	}

}
