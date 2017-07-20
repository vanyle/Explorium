package com.vanyle.generation;

import com.vanyle.engine.Block;
import com.vanyle.engine.Chunk;
import com.vanyle.engine.RandomGenerator;

public class WorldGenerator{
  private static final float smoothness = 30.0F;
  private static final float BIG_smoothness = 300f;
  
  private static void generateTree(int x, int y, double seed, Chunk c){
	RandomGenerator rand = new RandomGenerator(seed);
	
    int treesize = (int)(rand.next() * 5.0D + 4.0D);
    int branches = (int)(rand.next() * 2.0D) * 2;
    boolean AreLeaves = rand.next() > 0.5D;
    
    double[] branchesProperties = new double[treesize + 2];
    for (short i = 0; i < branches; i = (short)(i + 1))
    {
      int br = (int)(rand.next() * Math.abs(treesize - 2) + 2.0D);
      branchesProperties[br] = (rand.next() + 0.5D);
    }
    for (short i = 0; i < treesize; i = (short)(i + 1))
    {
      c.writeTopMatrix(x, y - i, (short)4);
      if (branchesProperties[i] != 0.0D)
      {
        double gradient = branchesProperties[i];
        int s = 1;
        for (short j = 0; j < 5; j = (short)(j + 1))
        {
          int xlog = (int)(x + gradient * j * s / 2.0D);
          int ylog = y - i - j / 2;
          s = -s;
          if ((c.getTopMatrix(xlog, ylog) == Block.BLOCK_ID_AIR) || (c.getTopMatrix(xlog, ylog) == Block.BLOCK_ID_LEAF)) {
            c.writeTopMatrix(xlog, ylog, Block.BLOCK_ID_LOG);
          }
          if (AreLeaves)
          {
            if (c.getTopMatrix(xlog + 1, ylog) == Block.BLOCK_ID_AIR) {
              c.writeTopMatrix(xlog + 1, ylog, Block.BLOCK_ID_LEAF);
            }
            if (c.getTopMatrix(xlog - 1, ylog) == Block.BLOCK_ID_AIR) {
             c.writeTopMatrix(xlog - 1, ylog, Block.BLOCK_ID_LEAF);
            }
            if (c.getTopMatrix(xlog, ylog - 1) == Block.BLOCK_ID_AIR) {
              c.writeTopMatrix(xlog, ylog - 1, Block.BLOCK_ID_LEAF);
            }
          }
        }
      }
    }
  }
  
  public static void generateChunk(Chunk c, double x, double y){
	RandomGenerator rand = new RandomGenerator(y);
    for (short i = 0; i < Chunk.ChunkSize; i++) {
      for (short j = 0; j < Chunk.ChunkSize; j++) {
        c.writeTopMatrix(i, j, Block.BLOCK_ID_AIR);
      }
    }
    
    double r1 = rand.next()*2.5f;
    double r2 = rand.next()*2.3f;
    

    
    for (int i = 0; i < Chunk.ChunkSize; i++){
    	int reali = (int)(x*Chunk.ChunkSize + i);

		int h = (int)((PerlinNoise.noise(reali / smoothness + 0.3, 0.2, 0.2 + r1)) * Chunk.ChunkSize / 2);
		h += (int)((PerlinNoise.noise(reali / BIG_smoothness + 0.5, 0.6, 0.3 + r2) + 0.5) * Chunk.ChunkSize / 2);
		
		if (RandomGenerator.IntegerCurve((int) reali,20) == 1){
			generateTree(i, h - 1,(int)(reali*5/7 + h), c);
		}
		c.writeTopMatrix(i, h, Block.BLOCK_ID_GRASS);
		for (int j = h + 1; j < Chunk.ChunkSize; j++) {
		    if (j - h - 1 < 2){
		    	c.writeTopMatrix(i, j, Block.BLOCK_ID_DIRT);
		    }else{
		    	c.writeTopMatrix(i, j, Block.BLOCK_ID_ROCK);
		    	if(PerlinNoise.noise(j / 10f + 0.3, reali / 10f + 0.3, 0.2 + r2) > 0.5) {
		    		c.writeTopMatrix(i, j, Block.BLOCK_ID_WATER);
		    	}
		    }
		}
    }
  }
}
