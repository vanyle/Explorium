package com.vanyle.procedural;

import com.vanyle.data.BlockData;
import com.vanyle.data.Structure;
import com.vanyle.data.StructureData;
import com.vanyle.math.VMath;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;

/**
 * 
 * @author vanyle
 *
 *	Generate a random world based on a seed
 */
public class ClassicGenerator extends Generator{
	
	
	private static final double cavefreq = 100;
	private static final double caveslopeL = 0.1;
	private static final double caveslopeV = -2.5;
	private static final double caveslopeB = -0.5; // to go back up
	private static final double cavesize = 16;
	
	private static final double structureFrequency = 40;
	
	// Control tree shape
	
	private static final double leavesRadiusSpanSquared = 5;
	private static final double leavesRadiusSquared = 12;
	private static final double minTreeHeight = 5;
	private static final double TreeHeightSpan = 10;
	
	private static final double rug = 3; // small perlin strenght
	private static final double vari = 20; // medium perlin strenght
	private static final double trend = 80; // big perlin strenght
	
	public ClassicGenerator(long seed) {
		super(seed);
	}
	public int floatint(double n,int max) { // map a number from -1 to 1 to 0 to max
		return (int)Math.floor((n/2+0.5)*max);
	}
	@Override
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
			strucutreBlock(i,j,p.cx*CSIZE+i,p.cy*CSIZE+j,c);
		}
	}
	public double getBiome(double xpos) {
		return VMath.noise(xpos/500f, seed+0.1, seed+0.6)*2;
	}
	public double getHeight(double xpos) {
		double h = 0;
		h += VMath.noise( (xpos)/13f, seed*2, seed) * 4; // small noise
		h += VMath.noise(xpos/300f + 0.1, seed*7, seed) * 120; // big noise ("plateau")
		h += CSIZE / 2; 
		return h;
	}
	public double[] nearestStructureBlock(double x,double y) {
		double xstruct = x - VMath.mod(x,structureFrequency);
		double ystruct = y - VMath.mod(y,structureFrequency);
		xstruct = Math.abs(xstruct-x) < Math.abs(xstruct+structureFrequency-x) ? xstruct : xstruct+structureFrequency;
		xstruct = Math.abs(xstruct-x) < Math.abs(xstruct-structureFrequency-x) ? xstruct : xstruct-structureFrequency;
		ystruct = Math.abs(ystruct-y) < Math.abs(ystruct+structureFrequency-y) ? ystruct : ystruct+structureFrequency;
		ystruct = Math.abs(ystruct-y) < Math.abs(ystruct-structureFrequency-y) ? ystruct : ystruct-structureFrequency;
		
		double[] pos = {
				xstruct + VMath.noise(xstruct/3f + 0.1, seed+0.1 ,seed-0.1) * structureFrequency/3,
				ystruct + VMath.noise(ystruct/3f + 0.1, seed-0.1 ,seed-0.3) * structureFrequency/3
			};
		return pos;
	}
	public void strucutreBlock(int i,int j,double x,double y,Chunk c) { // only allowed to edit i,j
		
		double[] pos = nearestStructureBlock(x, y);
		double h = getHeight(pos[0]);
		double biome = getBiome(pos[0]);
		boolean notree = false;
		
		for(int k = 0;k < StructureData.list.length;k++) {
			Structure cr = StructureData.list[k];
			// compute position of the nearest structure.
			if(cr.biomeRangeMaximum > biome && cr.biomeRangeMinimum < biome) {
				// compute height of the structure if adjustHeight
				if(VMath.noise(pos[0]*2 + 2.1, pos[0]*2 - 2.1, seed+0.3) > -1 + 2f/cr.rarity)
					continue;
				
				notree = true; // structure will likely generate here, dont put a tree
				
				boolean cave = isACave(pos[0], pos[1]);
				if(!cr.canBeinCave && cave)
					continue;
				if(cr.mustBeinCave && !cave)
					continue;
				
				if(cr.adjustHeight) {
					if(cr.HeightRangeMinimum > pos[1]-h-cr.getHeight()/2 && cr.HeightRangeMinimum < pos[1]-h+30-cr.getHeight()/2) { // 10 : adjust strength
						pos[1] = h+cr.HeightRangeMinimum;
					}else if(cr.HeightRangeMaximum < pos[1]-h+cr.getHeight()/2 && cr.HeightRangeMaximum > pos[1]-h-30+cr.getHeight()/2) {
						pos[1] = h+cr.HeightRangeMaximum;
					}
				}
				if(cr.HeightRangeMaximum < pos[1]-h)
					continue;
				if(cr.HeightRangeMinimum > pos[1]-h)
					continue;
				
				// generate structure assuming pos[0] ; pos[1] is the center
				for(int l = 0;l < cr.getWidth();l++) {
					for(int m = 0;m < cr.getHeight();m++) {
						if(Math.floor(x) == toInt(pos[0])+l-cr.getWidth()/2 && Math.floor(y) == toInt(pos[1])+m-cr.getHeight()/2) {
							c.data[i][j][0] = cr.getData(l, m);
							c.data[i][j][1] = cr.getGhostData(l, m);
						}
					}
				}
				return;
			}
		}
		
		boolean cave = isACave(pos[0], Math.floor(h)+1); // is cave below
		
		if(!cave && !notree) {
			if(getBiome(pos[0]) < 0.5 && y-h < 3) { // no tree in desert TODO cactus
				double treeheight = minTreeHeight + VMath.numerate(toInt(pos[0]),toInt(h), (int)(seed+3))*TreeHeightSpan;
				double treetop = h - treeheight;
				
				Position p = new Position(x,y,0,0);
				
				if(!isACave(pos[0], Math.floor(h)+1)) {
					if((Math.floor(pos[0]) == x || Math.floor(pos[0]) == x+1) && y > treetop){
						c.data[i][j][1] = BlockData.ID_TRUNK;
					}else if(p.dist(new Position(pos[0],treetop,0,0)) < leavesRadiusSquared + 
							leavesRadiusSpanSquared*VMath.noise(pos[0]/4f - 0.1f, seed+0.1f, seed*3)) {
						
						c.data[i][j][1] = BlockData.ID_LEAF;
					}
				}
			}
		}
	}
	public boolean isACave(double x,double y) {
		return Math.abs( VMath.mod((y - x*caveslopeL
				+ VMath.noise(x/250f, y/250f, seed*2+0.1f)*trend
				+ VMath.noise(x/20f, y/40f, seed*3-0.1f)*vari
				+ VMath.noise(x/5f, y/5f, seed*7+0.2f)*rug 
					),cavefreq) ) < cavesize ||
		 Math.abs( VMath.mod((y - x*caveslopeV
				+ VMath.noise(x/250f, y/250f, seed*5+1)*trend
				+ VMath.noise(x/20f, y/40f, seed*9+1)*vari
				+ VMath.noise(x/5f, y/5f, seed+1)*rug 
					),cavefreq * 6) ) < cavesize ||
		 Math.abs( VMath.mod((y - x*caveslopeB
					+ VMath.noise(x/250f, y/250f, seed*4.2+1)*trend
					+ VMath.noise(x/20f, y/40f, seed*5.6+1)*vari/2
					+ VMath.noise(x/5f, y/5f, seed*8.9+1)*rug 
						),cavefreq*10) ) < cavesize; // really rare
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
