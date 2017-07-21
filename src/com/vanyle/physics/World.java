package com.vanyle.physics;

import java.util.LinkedList;
import java.util.List;

import com.vanyle.life.Entity;
import com.vanyle.life.Player;
import com.vanyle.main.Explorium;
import com.vanyle.procedural.ClassicGenerator;
import com.vanyle.procedural.Generator;
import com.vanyle.procedural.InteriorGenerator;

public class World {
	
	public static final int REGULAR_WORLD = 0;
	public static final int INSIDE_WORLD = 1;
	public static final int UPSIDEDOWN_WORLD = 2;
	
	private static Position[] ppos = new Position[4]; // Store Player Position across worlds
	
	public int worldStat = 0;
	
	public static final int WORLD_DATA_SIZE = 3;
	Chunk[][] wdata = new Chunk[WORLD_DATA_SIZE][WORLD_DATA_SIZE];
	Chunk[][] tempwdata = new Chunk[WORLD_DATA_SIZE][WORLD_DATA_SIZE];
	private Generator g;
	
	public Player player = new Player();
	public Position campos = new Position(0,0,0,1);
	
	public List<Entity> entitylist = new LinkedList<>();
	
	private final int chunkoffset = (int)Math.floor(WORLD_DATA_SIZE/2); // here 1
	
	private Position currChunk = new Position(0,0,-1,-1); // "camera position" kinda, gives the position of a block inside wdata[1][1]
	
	// every coord is block based
	
	public World(Generator g) {
		this.g = g;
		loadAllChunk(new Position(0,0,0,2));
	}
	public void load() { // when ready to render
		player.p = new Position(0,0,0,1); // reset player pos on world creation
		entitylist.add(player);
		// loadInsideWorld(); // line for debugging specific worlds gens
	}
	public void loadChunk(Position p) { // try to generate as few chunks as posible
		if(p.cx == currChunk.cx && p.cy == currChunk.cy) {
			return;
		}
		Position differ = p.clone();
		differ.substract(currChunk); // compute differ
		currChunk.cx = p.cx;
		currChunk.cy = p.cy;
		
		Position pc;
		
		// Compute which chunks should be regenerated
		if(Math.abs(differ.cx) > WORLD_DATA_SIZE-1 || Math.abs(differ.cy) > WORLD_DATA_SIZE-1) { // all must be reloaded
			loadAllChunk(p);
			return;
		}

		for(int i = 0;i < WORLD_DATA_SIZE;i++) {
			for(int j = 0;j < WORLD_DATA_SIZE;j++) {
				try {
					tempwdata[i][j] = wdata[i+differ.getCX()][j+differ.getCY()];
				}catch(ArrayIndexOutOfBoundsException e) {
					pc = new Position(0,0,i-chunkoffset,j-chunkoffset);
					pc.add(currChunk);
					tempwdata[i][j] = g.generate(pc);
				}
			}
		}
		wdata = tempwdata;
		tempwdata = new Chunk[WORLD_DATA_SIZE][WORLD_DATA_SIZE];
	}
	public void loadAllChunk(Position p) {
		if(p.cx != currChunk.cx || p.cy != currChunk.cy) { // if an update is required
			currChunk.cx = p.cx;
			currChunk.cy = p.cy;
			
			Position pc;
			
			for(int i = 0;i < WORLD_DATA_SIZE;i++) {
				for(int j = 0;j < WORLD_DATA_SIZE;j++) {
					pc = new Position(0,0,i-chunkoffset,j-chunkoffset);
					pc.add(currChunk);
					wdata[i][j] = g.generate(pc);
				}
			}
		}
	}
	public void regenerate() {
		Position pc;
		for(int i = 0;i < WORLD_DATA_SIZE;i++) {
			for(int j = 0;j < WORLD_DATA_SIZE;j++) {
				pc = new Position(0,0,i-chunkoffset,j-chunkoffset);
				pc.add(currChunk);
				wdata[i][j] = g.generate(pc);
			}
		}
	}
	public int getData(Position p) {
		p.substract(currChunk); // convert to relative coords
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return 0; // chunk not loaded
		}
		return wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][0];
	}
	public int getBackgroundData(Position p) {
		p.substract(currChunk); // convert to relative coords
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return 0; // chunk not loaded
		}
		return wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][1];
	}
	
	public void setData(Position p,int id) {
		p.substract(currChunk); // convert to relative coords
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return; // chunk not loaded
		}
		wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][0] = id;
	}
	public void setBackgroundData(Position p,int id) {
		p.substract(currChunk); // convert to relative coords
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return; // chunk not loaded
		}
		wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][1] = id;
	}
	public int getRelativeData(Position p) {
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return 0; // chunk not loaded
		}
		return wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][0];
	}
	public int getRelativeBackgroundData(Position p) {
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return 0; // chunk not loaded
		}
		return wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][1];
	}
	public void setRelativeData(Position p,int id) {
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return; // chunk not loaded
		}
		wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][0] = id;
	}
	public void setRelativeBackgroundData(Position p,int id) {
		if(p.cx >= WORLD_DATA_SIZE || p.cx < 0 || p.cy >= WORLD_DATA_SIZE || p.cy < 0) {
			return; // chunk not loaded
		}
		wdata[p.getCX()][p.getCY()].data[p.getX()][p.getY()][1] = id;
	}
	
	public void loadInsideWorld() { // change generation, regen chunks and change player position
		if(worldStat != INSIDE_WORLD) {
			ppos[worldStat] = player.p.clone(); // save current position
			
			worldStat = World.INSIDE_WORLD;
			setGenerator(new InteriorGenerator(0l));
			
			player.p = new Position(Chunk.CSIZE+5,Chunk.CSIZE+5,0,0);
			campos = player.p.clone(); // replace camera correctly
			campos.add(new Position(-Chunk.CSIZE/2,-Chunk.CSIZE/4,0,0));
			
			if(player.p.cx != currChunk.cx || player.p.cy != currChunk.cy) { // if an update is required
				currChunk.cx = player.p.cx;
				currChunk.cy = player.p.cy;
			}
			regenerate();
		}
	}
	public void loadRegularWorld() {
		if(worldStat != REGULAR_WORLD) {
			ppos[worldStat] = player.p.clone(); // save current position
			
			worldStat = World.REGULAR_WORLD;
			setGenerator(new ClassicGenerator(Explorium.GLOBAL_SEED));
			
			player.p = ppos[World.REGULAR_WORLD].clone(); // load regular world position
			
			campos = player.p.clone(); // replace camera correctly
			campos.add(new Position(-Chunk.CSIZE/2,-Chunk.CSIZE/4,0,0));
			
			loadAllChunk(new Position(0,0,0,2));
			regenerate();
		}
	}
	
	public void setGenerator(Generator g) {
		this.g = g;
	}
	public Generator getGenerator() {
		return g;
	}
}
