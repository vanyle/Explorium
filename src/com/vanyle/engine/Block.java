package com.vanyle.engine;

public class Block
{
  public static final short BLOCK_ID_AIR = 0;
  public static final short BLOCK_ID_GRASS = 1;
  public static final short BLOCK_ID_DIRT = 2;
  public static final short BLOCK_ID_ROCK = 3;
  public static final short BLOCK_ID_LOG = 4;
  public static final short BLOCK_ID_LEAF = 5;
  public static final short BLOCK_ID_WATER = 6;
  public static final short BLOCK_ID_SAND = 7;
  public static final int MAX_BLOCK_ID = 8;
  public static final double[][] colors = {
	    { 0, 0, 0, 0}, 
	    { 0, 1, 0, 1 }, 
	    { 0.46, 0.28, 0, 1 }, 
	    { 0.5D, 0.5D, 0.5D, 1 }, 
	    { 0.5D, 0.32D, 0.0D, 1 }, 
	    { 0.031, 0.439, 0, 1 }, 
	    { 0, 0, 1, 0.5 }, 
	    { 1, 1, 0, 1}
    };
  
  public static double[] getColorFromId(int id)
  {
    return colors[id];
  }
}
