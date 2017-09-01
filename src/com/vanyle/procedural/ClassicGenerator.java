package com.vanyle.procedural;

import com.vanyle.blocks.Blocks;
import com.vanyle.data.Structure;
import com.vanyle.data.StructureData;
import com.vanyle.math.SimplexNoise;
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
	
	private static final double structureFrequency = 40;
	
	// Control tree shape
	
	private static final double leavesRadiusSpanSquared = 5;
	private static final double leavesRadiusSquared = 12;
	private static final double minTreeHeight = 5;
	private static final double TreeHeightSpan = 10;
	
	
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
			structureBlock(i,j,(int)p.cx * CSIZE+i,cheight * CSIZE+j,c);
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
		
		// list all the possible structures that are near and find the closest one
		
		double currNearX = 0,currNearY = 0;
		double xstruct,ystruct;
		
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 3;j++) {
				xstruct = x - VMath.mod(x,structureFrequency) + structureFrequency * (i-1);
				ystruct = y - VMath.mod(y,structureFrequency) + structureFrequency * (j-1);
				
				xstruct += VMath.noise(xstruct/3. + -12., ystruct/3. + 63., -42.4) * structureFrequency/3.;
				ystruct += VMath.noise(xstruct/3. + 42., ystruct/3. + -86., 32.4) * structureFrequency/3.;
				
				xstruct = toInt(xstruct);
				ystruct = toInt(ystruct);
				
				if((i == 0 && j == 0) || VMath.dist(xstruct,ystruct,x,y) < VMath.dist(currNearX,currNearY,x,y)) {
					currNearX = xstruct;
					currNearY = ystruct;
				}
			}
		}
		
		// find nearest structlist to x;y
		
		double[] pos = {
				currNearX,
				currNearY
			};
		return pos;
	}
	public void structureBlock(int i,int j,double x,double y,Chunk c) { // only allowed to edit i,j
		
		double[] pos = nearestStructureBlock(x, y);
		double h = getHeight(pos[0]);
		double biome = getBiome(pos[0]);
		boolean notree = false;
		
		for(int k = 0;k < StructureData.list.length;k++) {
			Structure cr = StructureData.list[k];
			// compute position of the nearest structure.
			if(cr.biomeRangeMaximum > biome && cr.biomeRangeMinimum < biome) {
	
				// Handel rarity
				if(VMath.noise(pos[0]*2 + 2.1, pos[0]*2 - 2.1, seed+0.3) > -1 + 2f/cr.rarity)
					continue;
				
				notree = true; // structure will likely generate here, dont put a tree
				
				boolean cave = isACave(pos[0], pos[1]);
				if(!cr.canBeinCave && cave)
					continue;
				if(cr.mustBeinCave && !cave)
					continue;
				
				// compute height of the structure if adjustHeight. adjust strenghth must be less than half  the structure frequency
				if(cr.adjustHeight) {
					if(cr.HeightRangeMinimum > pos[1]-h-cr.getHeight()/2 && cr.HeightRangeMinimum < pos[1]-h+20-cr.getHeight()/2) { // 20 : adjust strength
						pos[1] = h+cr.HeightRangeMinimum;
					}else if(cr.HeightRangeMaximum < pos[1]-h+cr.getHeight()/2 && cr.HeightRangeMaximum > pos[1]-h-20+cr.getHeight()/2) {
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
						if(toInt(x) == toInt(pos[0])+l-cr.getWidth()/2 && toInt(y) == toInt(pos[1])+m-cr.getHeight()/2) {
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
			if(getBiome(pos[0]) < 0.5 && Math.abs(pos[1]-h) < 10 && y-h < 3) { // no tree in desert TODO cactus
				double treeheight = minTreeHeight + VMath.numerate(toInt(pos[0]),toInt(h), (int)(seed+3))*TreeHeightSpan;
				double treetop = h - treeheight;
				
				Position p = new Position(x,y,0,0);
				
				if(!isACave(pos[0], Math.floor(h)+1)) {
					if((Math.floor(pos[0]) == x || Math.floor(pos[0]) == x+1) && y > treetop){
						c.data[i][j][1] = Blocks.BlockTrunk.id();
					}
					if(p.dist(new Position(pos[0],treetop,0,0)) < leavesRadiusSquared + 
							leavesRadiusSpanSquared*VMath.noise(pos[0]/4f - 0.1f, seed+0.1f, seed*3)) {
						
						c.data[i][j][1] = Blocks.BlockLeaf.id();
					}
				}
			}
		}
	}
	public boolean isACave(double x,double y) {
			return Math.abs(	
				//VMath.noise( x / 40f + 63f ,y / 20f + 41f , seed + 9.6f) +
				//VMath.noise( x / 40f + 33f ,y / 20f + 82f , seed + 7.5f) +
				SimplexNoise.noise( x / 70f + 73f ,y / 70f - 31f , seed - 8.5f) * 0.5 )
			< 0.1;
	}	
	public void skyGen(Chunk c,Position p,int i,int j) {
		double d = VMath.noise( (p.cx*CSIZE+i)/40f, (p.cy*CSIZE+j)/40f, seed);
		c.data[i][j][0] = d > 0.3 ? Blocks.BlockCloud.id() : Blocks.BlockAir.id();
	}
	public void plainsGen(Chunk c,Position p,int i,int j,double h) { // generate plains
		boolean cave = isACave(p.cx*CSIZE+i,p.cy*CSIZE+j);
		double jid = j + toInt(p.getCY())*CSIZE;
		if(!cave) {
			if(jid > h && jid < h+1){
				c.data[i][j][0] = Blocks.BlockGrass.id();
				if(getBiome(p.cx*CSIZE + i) > 0.5) {
					c.data[i][j][0] = Blocks.BlockSand.id();
				}
			}else if(jid > h){
				c.data[i][j][0] = Blocks.BlockDirt.id();
				if(getBiome(p.cx*CSIZE + i) > 0.5) { // biome : 1 - 0.5 => desert. 0.5 - -1 => plain
					c.data[i][j][0] = Blocks.BlockSand.id();
				}
			}
		}else{
			c.data[i][j][0] = Blocks.BlockAir.id(); // cave entrance
		}
	}
	public void caveGen(Chunk c,Position p,int i,int j,double h) { // generate a cave world
		boolean cave = isACave(p.cx*CSIZE+i,p.cy*CSIZE+j);
		int filler = Blocks.BlockStone.id();
		double waterc = getBiome(p.cx*CSIZE + i)/10f;
		
		if( getBiome(p.cx*CSIZE + i) > 0.5 && (p.cy*CSIZE+j - h)/100f < getBiome(p.cx*CSIZE + i)-0.5 ){
			filler = Blocks.BlockSand.id();
		}
		int r = cave ? Blocks.BlockAir.id() : filler;
		if(r == Blocks.BlockAir.id() && VMath.numerate((int)(p.cx*CSIZE+i),(int)(p.cy*CSIZE+j), 0) > (0.80+waterc)) {
			r = Blocks.BlockWater.id();
		}
		c.data[i][j][0] = r;
	}
	public int toInt(double d) {
		return (int)Math.floor(d);
	}
}
