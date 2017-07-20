package com.vanyle.engine;

public class World implements Runnable{
	private Chunk map;
	public static final double gravity = 0.3;
	public static final double friction = 0.2;
	private Player p = new Player();
	
	public World(){
		p.cX = 2;
		map = new Chunk(0, 0, 0);
	    map.protoGenerate(0.821432D,0.821432D);
	    new Thread(this).start();
	}
	public Chunk getChunk(){
		return map;
	}
	public Player getPlayer(){
		return p;
	}
	@Override
	public void run(){
		while(true){
			Physics.amplyPhysics(map, p);
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
	}
}
