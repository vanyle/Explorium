package com.vanyle.menu;

import java.awt.Color;
import java.awt.Graphics;

import com.vanyle.data.FontData;

public class TextArea extends Widget{

	private boolean focus = false;
	public int maxStringLength = 20;
	public boolean numberOnly = false;
	private Color bg = Color.white;
	private Color bgFocus = new Color(200,200,200);
	private Color textc = Color.black;
	
	public TextArea(String text,int x,int y,int size) {
		this.x = x;
		this.y = y;
		setText(text);
		setSize(size);
		setText(text);
	}
	public boolean isFocus() {
		return focus;
	}
	public void setMaxStringLength(int msl) {
		maxStringLength = msl;
		setText(getText());
	}
	@Override
	public void setText(String s) {
		text = s.substring(0, Math.min(maxStringLength,s.length()));
		s = "";
		for(int i = 0;i < maxStringLength;i++) {
			s += "0";
		}
		width = FontData.length(s, getSize())+padding*2;
	}
	@Override
	public boolean in(int x,int y) {
		return (focus = this.x-padding < x && this.y-padding < y && this.y+height-padding > y && this.x+width-padding > x);
	}
	@Override
	public void draw(Graphics g) {
		if(!focus)
			g.setColor(bg);
		else
			g.setColor(bgFocus);
		
		g.fillRect(x-padding,y-padding,width,height);
		g.setColor(textc);
		FontData.drawString(getText(), x, y, g, getSize());
	}
}
