package com.vanyle.engine;

import java.util.LinkedList;
import java.util.List;

import com.vanyle.generation.WorldGenerator;
/**
 * <h1>Chunk</h1>
 * Represente un morceau de monde de Chunk.ChunkSize * Chunk.ChunkSize en 2 dimension
 *
 */
public class Chunk{
	
	public static final int ChunkSize = 50;
	private short[][][] matrix = new short[ChunkSize][ChunkSize][2];
	private int xID = 0;
	private int yID = 0;
	private int zID = 0;
  
	private List<Entity> entitylist = new LinkedList<Entity>();
	
  public Chunk(int x, int y, int z){
    xID = x;
    yID = y;
    zID = z;
    WorldGenerator.generateChunk(this, x, y);
  }
  
  public int[] getCoord(){
    int[] c = { xID, yID, zID };
    return c;
  }
  
  public void writeTopMatrix(int x, int y, short id){
    if ((x >= 0) && (y >= 0) && (x < matrix.length) && (y < matrix[x].length)) {
      matrix[x][y][0] = id;
    }
  }
  
  public short getTopMatrix(int x, int y){
    if ((x >= 0) && (y >= 0) && (x < matrix.length) && (y < matrix[x].length)) {
      return matrix[x][y][0];
    }
    return Block.BLOCK_ID_AIR;
  }
	public void writeBackMatrix(int x, int y, short id){
		if ((x >= 0) && (y >= 0) && (x < matrix.length) && (y < matrix[x].length)) {
			matrix[x][y][1] = id;
		}
	}
	  
  public short getBackMatrix(int x, int y){
    if ((x >= 0) && (y >= 0) && (x < matrix.length) && (y < matrix[x].length)) {
      return matrix[x][y][1];
    }
    return Block.BLOCK_ID_AIR;
  }
  public Entity[] getEntities(){
	  return (Entity[])entitylist.toArray(new Entity[entitylist.size()]);
  }
  public void removeEntity(int id){
	  entitylist.remove(id);
  }
  public void addEntity(Entity e){
	  if(e != null)
		  entitylist.add(e);
  }
  
	public void rebuildChunk(int x, int y, int z){
		xID = x;
		yID = y;
		zID = z;
		WorldGenerator.generateChunk(this, x, y);
	}
  
	public void protoGenerate(double x, double y){
	  WorldGenerator.generateChunk(this, x, y);
	}
}
