package com.vanyle.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DisplayFeed{

	private ExploriumCanvas ec;
	
  protected void setRGB(Graphics graphics, double r, double g, double b, double a){
    r = r > 1.0D ? 1.0D : r;
    g = g > 1.0D ? 1.0D : g;
    b = b > 1.0D ? 1.0D : b;
    a = a > 1.0D ? 1.0D : a;
    if (graphics != null) {
      graphics.setColor(new Color((float)r, (float)g, (float)b, (float)a));
    }
  }
  
  protected void fillRect(Graphics g, double a, double b, double c, double d){
    if (g != null) {
      g.fillRect((int)a, (int)b, (int)c, (int)d);
    }
  }
  void setCanvas(ExploriumCanvas ec){
	  this.ec = ec;
  }
  public int getWidth(){
	  return ec.getWidth();
  }
  public int getHeight(){
	  return ec.getHeight();
  }
  protected void fillText(Graphics g, String s, double x, double y){
    if (g != null) {
      g.drawString(s, (int)x, (int)y + g.getFont().getSize());
    }
  }
  
  protected void fillCenteredText(Graphics g, String s, double x, double y){
    if (g != null)
    {
      int width = g.getFontMetrics().stringWidth(s);
      g.drawString(s, (int)x - width / 2, (int)y + g.getFont().getSize());
    }
  }
  
  protected void setFont(Graphics g, String fontname, double fontsize){
    if (g != null) {
      g.setFont(new Font(fontname, 0, (int)fontsize));
    }
  }
  
  protected void setFontSize(Graphics g, double fontsize){
    if (g != null) {
      g.setFont(g.getFont().deriveFont((float)fontsize));
    }
  }
  
  protected boolean contains(double x, double y, double w, double h, double x1, double y1){
    if ((x < x1) && (y < y1) && (x + w > x1) && (y + h > y1)) {
      return true;
    }
    return false;
  }
  
  public void draw(BufferedImage bi) {}
  
  public void onClick(int x, int y) {}
  
  public void onMouseMove(int x, int y) {}
  
  public void onMouseDown(int x, int y) {}
  
  public void onKeyPressed(int id) {}
  
  public boolean onWindowClosed()
  {
    return true;
  }
}
