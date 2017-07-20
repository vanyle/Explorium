package com.vanyle.graphics;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import com.vanyle.loader.Explorium;

public class ExploriumFrame
  extends JFrame
  implements KeyListener, MouseInputListener
{
  private static final long serialVersionUID = 1L;
  private static ExploriumCanvas ec;
  private DisplayThread dt;
  private DisplayFeed df = new DisplayFeed();
  
  public ExploriumFrame()
  {
    super("Explorium " + Explorium.getConfig("version"));
    setup();
  }
  
  public ExploriumFrame(DisplayFeed df)
  {
    super("Explorium " + Explorium.getConfig("version"));
    setDisplayFeed(df);
    setup();
  }
  
  private void setup()
  {
    setVisible(true);
    setSize(800, 600);
    setDefaultCloseOperation(0);
    
    ec = new ExploriumCanvas(this);
    
    addKeyListener(this);
    setCursor(getToolkit().createCustomCursor(
      new BufferedImage(3, 3, 2), new Point(0, 0), 
      "null"));
    
    ec.addMouseListener(this);
    ec.addMouseMotionListener(this);
    
    ec.setDisplayFeed(df);
    setContentPane(ec);
    
    revalidate();
    
    dt = new DisplayThread(ec);
    dt.lockFPS(100);
    new Thread(dt).start();
  }
  
  public int getFPS()
  {
    return dt.getFPS();
  }
  
  public void setDisplayFeed(DisplayFeed df)
  {
    this.df = df;
    ec.setDisplayFeed(df);
  }
  
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if (e.getID() == 201) {
      if (df != null)
      {
        if (df.onWindowClosed()) {
          System.exit(0);
        }
      }
      else {
        System.exit(0);
      }
    }
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if (df != null) {
      df.onClick(e.getX(), e.getY());
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e)
  {
    if (df != null) {
      df.onMouseDown(e.getX(), e.getY());
    }
  }
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mouseDragged(MouseEvent e)
  {
    if (df != null) {
      df.onMouseMove(e.getX(), e.getY());
    }
  }
  
  public void mouseMoved(MouseEvent e)
  {
    if (df != null) {
      df.onMouseMove(e.getX(), e.getY());
    }
  }
  
  public void keyPressed(KeyEvent e)
  {
    if (df != null) {
      df.onKeyPressed(e.getKeyCode());
    }
  }
  
  public void keyReleased(KeyEvent e) {}
  
  public void keyTyped(KeyEvent e) {}
}
