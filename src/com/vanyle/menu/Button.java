package com.vanyle.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.vanyle.data.FontData;

public class Button extends Widget{

	BufferedImage bg = RenderMenu.buttonbg;
	public boolean pressable = true;
	
	public Button(String text,int x,int y,int size) {
		this.x = x;
		this.y = y;
		setText(text);
		setSize(size);
	}
	public void setBG(BufferedImage bg) {
		this.bg = bg;
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(bg,x-padding,y-padding,width,height, null);
		FontData.drawString(getText(), x, y, g, getSize());
	}
}
