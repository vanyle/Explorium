package com.vanyle.graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.vanyle.engine.Chunk;
import com.vanyle.engine.Entity;
import com.vanyle.engine.Player;
import com.vanyle.engine.World;
import com.vanyle.loader.Explorium;
import com.vanyle.render_utils.WorldDisplayer;

public class Game extends DisplayFeed{
	
  private ExploriumFrame ef;
  private int mousex = 0;
  private int mousey = 0;
  private World w = new World();
  
  public Game(ExploriumFrame ef){
    this.ef = ef;
  }
  
  public void draw(BufferedImage bi){
    drawWorld(bi);
    drawEntities(bi);
    drawFinal(bi);
  }
  
  public void drawWorld(BufferedImage bi){
    WorldDisplayer.matrixToWorld(bi, w.getChunk());
  }
  
  public void drawEntities(BufferedImage bi) {
	  Graphics g = bi.getGraphics();
	  Entity[] entities = w.getChunk().getEntities();
	  for(int i = 0;i < entities.length;i++){
		  
	  }
	  
	  double widthunit = bi.getWidth() / 100.0F;
	  //double heightunit = bi.getHeight() / 100.0F;
	  double blocksize = widthunit * 100.0D / Chunk.ChunkSize;

	  setRGB(g,1,0,0,1);
	  fillRect(g,w.getPlayer().getX()*blocksize,
			  w.getPlayer().getY()*blocksize, blocksize, blocksize*2);
  }
  
  public void drawFinal(BufferedImage bi){
    Graphics g = bi.getGraphics();
    setFontSize(g, 12.0D);
    setRGB(g, 0.0D, 0.0D, 0.0D, 1.0D);
    Player p = w.getPlayer();
    fillText(g, ef.getFPS() + "fps :"+(int)p.getX()+" ; "+(int)p.getY(), 0.0D, 0.0D);
    g.fillRect(mousex - 5, mousey - 5, 10, 10);
  }
  @Override
  public void onMouseMove(int x, int y){
    mousex = (int)(x*Explorium.resolutionX/ef.getWidth());
    mousey = (int)(y*Explorium.resolutionY/ef.getHeight());
  }
  @Override
  public void onKeyPressed(int id){
	  if(id == KeyEvent.VK_SPACE){
		  w.getPlayer().jump();
	  }else if(id == KeyEvent.VK_Q){
		  w.getPlayer().moveRight();
	  }else if(id == KeyEvent.VK_D){
		  w.getPlayer().moveLeft();
	  }
  }
}
