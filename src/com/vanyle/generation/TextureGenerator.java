package com.vanyle.generation;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.engine.Block;
import com.vanyle.engine.RandomGenerator;

public class TextureGenerator
{
  private static double randomstate = 0.5D;
  private static RandomGenerator rand = new RandomGenerator(0.524D);
  
  private static BufferedImage generatePerlinTexture(double r, double g, double b, double seed, int w, int h, double topchange, double widthchange, double noiseamount)
  {
    BufferedImage bi = new BufferedImage(w, h, 5);
    for (short i = 0; i < w; i = (short)(i + 1)) {
      for (short j = 0; j < h; j = (short)(j + 1))
      {
        double noise = PerlinNoise.noise(i * widthchange + 0.1D, j * topchange + 0.1D, 0.5D + seed) * noiseamount;
        double[] lum = luminosity(r, g, b, noise);
        int color = toRGBInteger(lum[0], lum[1], lum[2]);
        bi.setRGB(i, j, color);
      }
    }
    return bi;
  }
  
  private static double[] luminosity(double r, double g, double b, double flux)
  {
    double[] returns = {
      Math.max(0.0D, Math.min(r + flux, 1.0D)), 
      Math.max(0.0D, Math.min(g + flux, 1.0D)), 
      Math.max(0.0D, Math.min(b + flux, 1.0D)) };
    
    return returns;
  }
  
  private static int toRGBInteger(double r, double g, double b)
  {
    return new Color((float)r, (float)g, (float)b).getRGB();
  }
  
  public static BufferedImage[] rocks = new BufferedImage[3];
  public static BufferedImage[] dirts = new BufferedImage[3];
  public static BufferedImage[] grass = new BufferedImage[3];
  public static BufferedImage[] logs = new BufferedImage[3];
  public static BufferedImage[] waters = new BufferedImage[3];
  public static BufferedImage[] leaves = new BufferedImage[3];
  public static BufferedImage[] air = { new BufferedImage(16, 16, 6) };
  
  static
  {
    for (short i = 0; i < rocks.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(3);
      rocks[i] = generatePerlinTexture(color[0], color[1], color[2], i * 12 / 13, 16, 16, 0.6D, 0.6D, 0.1D);
    }
    for (short i = 0; i < dirts.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(2);
      dirts[i] = generatePerlinTexture(color[0], color[1], color[2], i * 13 / 12, 16, 16, 0.999D, 0.999D, 0.1D);
    }
    for (short i = 0; i < logs.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(4);
      logs[i] = generatePerlinTexture(color[0], color[1], color[2], i * 13 / 12, 16, 16, 0.2D, 0.6D, 0.1D);
    }
    for (short i = 0; i < waters.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(6);
      waters[i] = generatePerlinTexture(color[0], color[1], color[2], i * 13 / 12, 16, 16, 0.6D, 0.6D, 0.3D);
    }
    for (short i = 0; i < leaves.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(5);
      leaves[i] = generatePerlinTexture(color[0], color[1], color[2], i * 13 / 12, 16, 16, 0.6D, 0.6D, 0.1D);
    }
    for (short i = 0; i < grass.length; i = (short)(i + 1)){
      double[] color = Block.getColorFromId(2);
      grass[i] = generatePerlinTexture(color[0], color[1], color[2], i * 13 / 12, 16, 16, 0.999D, 0.999D, 0.1D);
      for (short j = 0; j < grass[i].getWidth(); j = (short)(j + 1))
      {
        color = Block.getColorFromId(1);
        for (short k = 0; k < rand.nextDouble() * 4.0D + 1.0D; k = (short)(k + 1))
        {
          double noise = PerlinNoise.noise(i * 12 / 13 + 0.2F, j * 13 / 12 + 0.2F, k * 6 / 7 + 0.2F);
          double[] newcolors = luminosity(color[0], color[1], color[2], noise);
          Color c = new Color((float)newcolors[0], (float)newcolors[1], (float)newcolors[2]);
          grass[i].setRGB(j, k, c.getRGB());
        }
      }
    }
    for (short i = 0; i < air.length; i = (short)(i + 1))
    {
      air[i].getGraphics().setColor(new Color(1, 0, 0, 128));
      air[i].getGraphics().fillRect(0, 0, 16, 16);
    }
  }
  
  public static BufferedImage getTextureFromBlockId(int id, double seed)
  {
    randomstate = Math.abs(seed * 10.0D + 3.0D);
    switch (id)
    {
    case 3: 
      return rocks[((int)Math.floor(randomstate * 1000.0D) % rocks.length)];
    case 2: 
      return dirts[((int)Math.floor(randomstate * 1000.0D) % dirts.length)];
    case 1: 
      return grass[((int)Math.floor(randomstate * 1000.0D) % grass.length)];
    case 4: 
      return logs[((int)Math.floor(randomstate * 1000.0D) % logs.length)];
    case 6: 
      return waters[((int)Math.floor(randomstate * 1000.0D) % waters.length)];
    case 5: 
      return leaves[((int)Math.floor(randomstate * 1000.0D) % leaves.length)];
    }
    return air[((int)Math.floor(randomstate * 1000.0D) % air.length)];
  }
}
