package com.vanyle.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import com.vanyle.loader.Explorium;

public class ExploriumCanvas
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  private DisplayFeed df;
  private ExploriumFrame ef;
  
  public ExploriumCanvas(ExploriumFrame ef){
    this.ef = ef;
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    BufferedImage bi = new BufferedImage(Explorium.resolutionX, Explorium.resolutionY, BufferedImage.TYPE_4BYTE_ABGR);
    if (df != null) {
      df.draw(bi);
    }
    g.drawImage(bi, 0, 0, ef.getWidth(), ef.getHeight(), null);
  }
  
  public void setDisplayFeed(DisplayFeed df)
  {
    this.df = df;
  }
}
