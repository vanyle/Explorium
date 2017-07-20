package com.vanyle.render_utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.vanyle.engine.Block;
import com.vanyle.engine.Chunk;
import com.vanyle.generation.PerlinNoise;
import com.vanyle.generation.TextureGenerator;

public class WorldDisplayer
{
  public static double blocksize = 20.0D;
  
  public static void matrixToWorld(BufferedImage bi, Chunk c)
  {
    double widthunit = bi.getWidth() / 100.0F;
    double heightunit = bi.getHeight() / 100.0F;
    blocksize = widthunit * 100.0D / Chunk.ChunkSize;
    Graphics2D g = (Graphics2D)bi.getGraphics();
    
    setRGB(g, 0.0195D, 0.6015625D, 0.953125D, 1.0D);
    fillRect(g, 0.0D, 0.0D, widthunit * 100.0D, heightunit * 100.0D);
    for (short i = 0; i < Chunk.ChunkSize; i++) {
      for (short j = 0; j < Chunk.ChunkSize; j++) {
        if (c.getTopMatrix(i,j) != Block.BLOCK_ID_AIR) {
          g.drawImage(TextureGenerator.getTextureFromBlockId(c.getTopMatrix(i,j), PerlinNoise.noise(i * 13 / 12, j * 12 / 13.0F, 0.8D)), 
            (int)(i * blocksize), 
            (int)(j * blocksize), 
            (int)(blocksize + 1.0D), 
            (int)(blocksize + 1.0D), null);
        }else if(c.getBackMatrix(i,j) != Block.BLOCK_ID_AIR){
        	g.drawImage(TextureGenerator.getTextureFromBlockId(c.getBackMatrix(i,j), PerlinNoise.noise(i * 13 / 12, j * 12 / 13.0F, 0.8D)), 
                    (int)(i * blocksize), 
                    (int)(j * blocksize), 
                    (int)(blocksize + 1.0D), 
                    (int)(blocksize + 1.0D), null);
        }
      }
    }
  }
  
  private static void setRGB(Graphics graphics, double r, double g, double b, double a)
  {
    r = r > 1.0D ? 1.0D : r;
    g = g > 1.0D ? 1.0D : g;
    b = b > 1.0D ? 1.0D : b;
    a = a > 1.0D ? 1.0D : a;
    if (graphics != null) {
      graphics.setColor(new Color((float)r, (float)g, (float)b, (float)a));
    }
  }
  
  private static void fillRect(Graphics g, double a, double b, double c, double d)
  {
    if (g != null) {
      g.fillRect((int)a, (int)b, (int)c, (int)d);
    }
  }
}
