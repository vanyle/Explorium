package com.vanyle.physics;

import java.util.Random;

import com.vanyle.data.BlockData;
import com.vanyle.math.VMath;

/**
 * 
 * @author vanyle
 *
 *	Generate a random world based on a seed
 */
public class Generator {
	
	private double seed = Math.random();
	private static final double CSIZE = Chunk.CSIZE;
	private static final double cavefreq = 100;
	
	private static final double caveslopeL = 0.1;
	private static final double caveslopeV = -2.5;
	private static final double caveslopeB = 1; // to go back up
	
	private static final double structureFrequency = 40;
	
	// Control tree shape
	
	private static final double leavesRadiusSpanSquared = 5;
	private static final double leavesRadiusSquared = 12;
	private static final double minTreeHeight = 5;
	private static final double TreeHeightSpan = 10;
	
	private static final double cavesize = 16;
	
	private static final double rug = 3; // small perlin strenght
	private static final double vari = 20; // medium perlin strenght
	private static final double trend = 80; // big perlin strenght
	
	public Generator(long seed) {
		this.seed = new Random(seed).nextDouble();
	}
	public double seed() {
		return seed;
	}
	public int floatint(double n,int max) { // map a number from -1 to 1 to 0 to max
		return (int)Math.floor((n/2+0.5)*max);
	}
	/**
	 * Generate a chunk from the coords of a block located inside the chunk.
	 * @param x
	 * @param y
	 * @return
	 */
	public Chunk generate(Position p) {
		Chunk c = new Chunk();
		int i;
		double h;
		int cheight = (int)Math.floor(p.getCY());
		
		for(i = 0;i < CSIZE;i++) {
			h = getHeight(i+p.cx*CSIZE); 
			generateCol(c,p,i,cheight,h);
		}
		return c;
	}
	public void generateCol(Chunk c,Position p,int i,int cheight,double h) {
		for(int j = 0;j < CSIZE;j++) {
			if(cheight*CSIZE + j > h-20 && cheight*CSIZE + j < h+3) {
				plainsGen(c,p,i,j,h);
			}else if(cheight * CSIZE + j > h) { // cave
				caveGen(c,p,i,j,h);
			}else if(cheight * CSIZE + j < h-20){
				skyGen(c,p,i,j);
			}
		}
	}
	public double getBiome(double xpos) {
		return VMath.noise(xpos/500f, seed+0.1, seed+0.6)*2;
	}
	public double getHeight(double xpos) {
		double h = 0;
		h += VMath.noise( (xpos)/13f, seed, seed) * 4; // small noise
		h += VMath.noise(xpos/300f + 0.1, seed*7, seed) * 120; // big noise ("plateau")
		h += CSIZE / 2; 
		return h;
	}
	public int strucutreBlock(double x,double y) {
		// find nearest structure
		// check is not in desert
		double xstruct = x - VMath.mod(x,structureFrequency);
		xstruct = Math.abs(xstruct-x) < Math.abs(xstruct+structureFrequency-x) ? xstruct : xstruct+structureFrequency;
		xstruct = Math.abs(xstruct-x) < Math.abs(xstruct-structureFrequency-x) ? xstruct : xstruct-structureFrequency;
		
		double noise = VMath.noise(xstruct/3f + 0.1, seed+0.1 ,seed-0.1) * structureFrequency/3;
		double realx = xstruct + noise;
		double h = getHeight(realx);
		
		boolean cave = isACave(realx, Math.floor(h)+1); // is cave below
	
		if(!cave) {
			// generate strucutres, TODO use StructureData class to generate structure
			if(Math.floor(VMath.mod(realx,structureFrequency)) == 0) { // no tree but a house
				
				if(x - realx < 10 && x-realx >= 0 && y-h < 2 && y-h > -8) {
					// house boundries
					double rx = Math.floor(x - realx);
					double ry = -Math.floor(y-h-1);
					if(ry == 0 || ry == 9 || rx == 0 || rx == 9) {
						return BlockData.ID_TRUNK;
					}
					if(rx == 3 && ry == 3) {
						return BlockData.ID_TRUNK;
					}
					return BlockData.ID_SAND;
				}
				
			}else if(getBiome(realx) < 0.5) { // no tree in desert TODO cactus
				
				double treeheight = minTreeHeight + VMath.numerate(toInt(realx),toInt(h), (int)(seed+3))*TreeHeightSpan;
				double treetop = h - treeheight;
				
				Position p = new Position(x,y,0,0);
				
				if(!isACave(realx, Math.floor(h)+1)) {
					if((Math.floor(realx) == x || Math.floor(realx) == x+1) && y > treetop){
						return BlockData.ID_TRUNK;
					}else if(p.dist(new Position(realx,treetop,0,0)) < leavesRadiusSquared + 
							leavesRadiusSpanSquared*VMath.noise(realx/4f - 0.1f, seed+0.1f, seed*3)) {
						
						return BlockData.ID_LEAF;
					}
				}
			}
		}
		return -1;
	}
	public boolean isACave(double x,double y) {
		return Math.abs( VMath.mod((y - x*caveslopeL
				+ VMath.noise(x/250f, y/250f, seed)*trend
				+ VMath.noise(x/20f, y/40f, seed)*vari
				+ VMath.noise(x/5f, y/5f, seed)*rug 
					),cavefreq) ) < cavesize ||
		 Math.abs( VMath.mod((y - x*caveslopeV
				+ VMath.noise(x/250f, y/250f, seed+1)*trend
				+ VMath.noise(x/20f, y/40f, seed+1)*vari
				+ VMath.noise(x/5f, y/5f, seed+1)*rug 
					),cavefreq * 6) ) < cavesize ||
		 Math.abs( VMath.mod((y - x*caveslopeB
					+ VMath.noise(x/250f, y/250f, seed+1)*trend
					+ VMath.noise(x/20f, y/40f, seed+1)*vari
					+ VMath.noise(x/5f, y/5f, seed+1)*rug 
						),cavefreq * 20) ) < cavesize; // really rare
	}	
	public void skyGen(Chunk c,Position p,int i,int j) {
		double d = VMath.noise( (p.cx*CSIZE+i)/40f, (p.cy*CSIZE+j)/40f, seed);
		c.data[i][j][0] = d > 0.3 ? BlockData.ID_CLOUD : BlockData.ID_AIR;
	}
	public void plainsGen(Chunk c,Position p,int i,int j,double h) { // generate plains
		boolean cave = isACave(p.cx*CSIZE+i,p.cy*CSIZE+j);
		double jid = j + toInt(p.getCY())*CSIZE;
		if(!cave) {
			if(jid > h && jid < h+1){
				c.data[i][j][0] = BlockData.ID_GRASS;
				if(getBiome(p.cx*CSIZE + i) > 0.5) {
					c.data[i][j][0] = BlockData.ID_SAND;
				}
			}else if(jid > h){
				c.data[i][j][0] = BlockData.ID_DIRT;
				if(getBiome(p.cx*CSIZE + i) > 0.5) {
					c.data[i][j][0] = BlockData.ID_SAND;
				}
			}
		}else{
			c.data[i][j][0] = BlockData.ID_AIR; // cave entrance
		}
		int edit = strucutreBlock(p.cx*CSIZE+i,p.cy*CSIZE+j);
		if(edit != -1)
			c.data[i][j][1] = edit;
	}
	public void caveGen(Chunk c,Position p,int i,int j,double h) { // generate a cave world
		boolean cave = isACave(p.cx*CSIZE+i,p.cy*CSIZE+j);
		int filler = BlockData.ID_STONE;
		double waterc = getBiome(p.cx*CSIZE + i)/10f;
		
		if( getBiome(p.cx*CSIZE + i) > 0.5 && (p.cy*CSIZE+j - h)/100f < getBiome(p.cx*CSIZE + i)-0.5 ){
			filler = BlockData.ID_SAND;
		}
		int r = cave ? BlockData.ID_AIR : filler;
		if(r == BlockData.ID_AIR && VMath.numerate((int)(p.cx*CSIZE+i),(int)(p.cy*CSIZE+j), 0) > (0.80+waterc)) {
			r = BlockData.ID_WATER;
		}
		c.data[i][j][0] = r;
	}
	public int toInt(double d) {
		return (int)Math.floor(d);
	}
}
