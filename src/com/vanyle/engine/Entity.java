package com.vanyle.engine;

public abstract class Entity {
	protected int x = 0;
	protected int y = 0;
	protected int z = 0;
	
	protected double velocityX = 0;
	protected double velocityY = 0;
	
	protected double cX = 0;
	protected double cY = 0;
	
	protected int height = 1;
	protected int width = 1;
	
	private int health = 20;
	private int maxhealth = 20;
	private int strengh = 1;
	private Item[] inventory = new Item[3];
	private Item holding;
	
	protected byte AI = 0;
	
	public static final byte AI_static = 0;
	public static final byte AI_passive = 1;
	public static final byte AI_follow = 2;
	public static final byte AI_neutral = 3;
	public static final byte AI_runaway = 4;
	public static final byte AI_hostile = 5;
	public static final byte AI_control = 6;
	
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxhealth;
	}
	public int getAttackValue(){
		if(holding == null)
			return strengh;
		return holding.getAttackValue() + strengh;
	}
	public int getInventorySize(){
		return inventory.length;
	}
	public void setItem(Item i,int slot){
		inventory[Math.min(Math.max(slot,0), inventory.length-1)] = i;
	}
	public double getX(){
		return cX;
	}
	public double getY(){
		return cY;
	}
	public int[] getChunkFromMap(){
		return new int[]{x,y,z};
	}
	public void AI(Chunk c,Player p){
		cX += velocityX;
		for(int i = 0;i < 10;i++){
			if(c.getTopMatrix((int)cX, (int)(cY + 0.1 + height)) != Block.BLOCK_ID_AIR && velocityY > 0){
				velocityY = 0; // consistence du sol
				break;
			}
			cY += velocityY/10;
		}
		
		if(c.getTopMatrix((int)cX, (int)(cY + 0.1 + height)) == Block.BLOCK_ID_AIR && cY < Chunk.ChunkSize){
			velocityY += World.gravity; // gravité
		}
		
		velocityY -= velocityY*World.friction; // friction
		velocityX -= velocityX*World.friction;
		
		switch(AI){
			case AI_static:
				break;
			default:
				
		}
	}
}
