package com.vanyle.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.vanyle.engine.Chunk;
import com.vanyle.loader.Explorium;
import com.vanyle.render_utils.WorldDisplayer;

public class Menu extends DisplayFeed{
  private ExploriumFrame ef;
  private int mousex = 0;
  private int mousey = 0;
  private final double widthunit = Explorium.resolutionX/100;
  private final double heightunit = Explorium.resolutionY/100;
  private final int[][] buttons = { { 50, 10, 50, 5 }, { 50, 60, 10, 4 } };
  private boolean[] hovered = new boolean[2];
  private final String[] elementPath = { "textures/menu/title.png", "textures/menu/button_play.png" };
  private static final int GRAPHIC_BUTTON_PLAY = 1;
  private static Chunk c = new Chunk(0, 0, 0);
  private static final int padding = 20;
  Graphics2D g;
  
  public Menu(ExploriumFrame ef){
    this.ef = ef;
  }
  
  public void draw(BufferedImage bi)
  {
    g = ((Graphics2D)bi.getGraphics());
    
    WorldDisplayer.matrixToWorld(bi, c);
    for (short i = 0; i < buttons.length; i = (short)(i + 1))
    {
      double[] pos = getButtonBox(g, i);
      Image currentImage = new ImageIcon(elementPath[i]).getImage();
      g.drawImage(currentImage, (int)pos[0], (int)pos[1], (int)pos[2], (int)pos[3], null);
      if (hovered[i])
      {
        setRGB(g, 0.0D, 0.0D, 0.0D, 0.2D);
        fillRect(g, pos[0], pos[1], pos[2], pos[3]);
      }
    }
    setFontSize(g, 12.0D);
    setRGB(g, 0.0D, 0.0D, 0.0D, 1.0D);
    fillText(g, ef.getFPS() + "fps", 0.0D, 0.0D);
    g.fillRect(mousex - 5, mousey - 5, 10, 10);
  }
  
  private double[] getButtonBox(Graphics g, short i)
  {
    if (g == null) {
      return new double[4];
    }
    if (i == 0) {
      setFont(g, "Times", 60.0D);
    } else {
      setFont(g, "Times", 30.0D);
    }
    double[] r = {
    		widthunit * buttons[i][0] - 20.0D - buttons[i][2] / 2 * widthunit, 
    		heightunit * buttons[i][1] - 20.0D, 
    		padding*2 + buttons[i][2] * widthunit, 
    		padding*2 + buttons[i][3] * widthunit };
    
    return r;
  }
  
  public void onMouseMove(int x, int y){
	    mousex = (int)(x*Explorium.resolutionX/ef.getWidth());
	    mousey = (int)(y*Explorium.resolutionY/ef.getHeight());
		    
	    for (short i = 0; i < buttons.length; i = (short)(i + 1)){
	      double[] box = getButtonBox(g, i);
	      if ((contains(box[0], box[1], box[2], box[3], x, y)) && (i != 0)) {
	        hovered[i] = true;
	      } else {
	        hovered[i] = false;
	      }
	    }
  }
  
  public void onMouseDown(int x, int y){
	  
	    for (short i = 0; i < buttons.length; i = (short)(i + 1)){
		      double[] box = getButtonBox(g, i);
		      if ((contains(box[0], box[1], box[2], box[3], mousex, mousey)) && 
			        (i == GRAPHIC_BUTTON_PLAY)) {
			        ef.setDisplayFeed(new Game(ef));
		      }
	    }
  }
}
