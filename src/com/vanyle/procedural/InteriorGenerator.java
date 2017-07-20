package com.vanyle.procedural;

import java.util.Random;

import com.vanyle.data.BlockData;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;

public class InteriorGenerator extends Generator{
	
	private int ZONE_WIDTH = 40;
	private int ZONE_HEIGHT = 40;
	private int WALL_MATERIAL = BlockData.ID_TRUNK;
	
	private int[] floorData;
	private int[][] wallData; // floor dependent.
	private int[] floorHoles;
	
	public InteriorGenerator(long seed) {
		super(seed+1);
		Random r = new Random(seed+1);
		int floorcount = (int)Math.floor((r.nextDouble()/2 + 0.75)*ZONE_HEIGHT/8); // 1 floor ~ 10 blocks
		int hleft = ZONE_HEIGHT;
		
		floorData = new int[floorcount];
		floorHoles = new int[floorcount];
		wallData = new int[floorcount][];
		
		for(int i = 0;i < floorcount;i++) { // Split building into floors
			if(i != floorcount-1)
				floorData[i] = (int)((hleft / (floorcount-(double)i)) + r.nextDouble()*3); 
			else
				floorData[i] = hleft;
			hleft -= floorData[i];
		}
		shuffleArray(floorData,r);
		for(int i = 1;i < floorcount;i++) {
			floorData[i] += floorData[i - 1];
		}
		
		for(int i = 0;i < floorcount;i++) { // Split floors into rooms
			int roomcount = (int)Math.floor((r.nextDouble()/2 + 0.75)*ZONE_WIDTH/10); // 1 room ~ 10 blocks
			int wleft = ZONE_WIDTH;
			wallData[i] = new int[roomcount];
			
			for(int j = 0;j < roomcount;j++) { // Split building into floors
				if(j != roomcount-1)
					wallData[i][j] = (int)((wleft / (roomcount-(double)j)) + r.nextDouble()*3); 
				else
					wallData[i][j] = wleft;
				wleft -= wallData[i][j];
			}
			shuffleArray(wallData[i],r);
			for(int j = 1;j < roomcount;j++) {
				wallData[i][j] += wallData[i][j - 1];
			}
		}
		
		// choose floor holes
		boolean ok = false;
		for(int i = 0;i < floorcount;i++) {
			while(!ok) {
				floorHoles[i] = (int)(r.nextDouble()*(ZONE_WIDTH-1))+1;
				
				for(int j = 0;j < wallData[i].length;j++) {
					if(wallData[i][j] == floorHoles[i] || wallData[i][j] == floorHoles[i]+1) {
						break;
					}
					if(j == wallData[i].length-1)
						ok = true;
				}
			}
			ok = false;
		}
	}
	 private static void shuffleArray(int[] ar,Random r){
	    for (int i = ar.length - 1; i > 0; i--){
	      int index = r.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	
	@Override
	public Chunk generate(Position p) {
		Chunk c = new Chunk();
		double x,y;
		
		for(int i = 0;i < CSIZE;i++) {
			for(int j = 0;j < CSIZE;j++) {
				x = i+CSIZE*p.cx;
				y = j+CSIZE*p.cy;
				
				if(x <= ZONE_WIDTH && x >= 0 && y <= ZONE_HEIGHT && y >= 0) {
					// inside
					if(x == ZONE_WIDTH || y == ZONE_HEIGHT || x == 0 || y == 0){
						c.data[i][j][0] = WALL_MATERIAL;
					}
					for(int k = 0;k < floorData.length;k++) {
						if(y == floorData[k] && x != floorHoles[k] && x != floorHoles[k]+1 && x != floorHoles[k]+2) {
							c.data[i][j][0] = WALL_MATERIAL;
							continue;
						}
						if(k != floorData.length-1 && y > floorData[k] && y < floorData[k+1]) { // y is at the correct floor
							for(int l = 0;l < wallData[k].length;l++) {
								if(x == wallData[k][l] && y < floorData[k+1]-4) { // generate walls
									c.data[i][j][0] = WALL_MATERIAL;
								}
							}
						}
					}
				}else{
					c.data[i][j][0] = BlockData.ID_EMPTYNESS;
				}
			}
		}
		return c;
	}
}
