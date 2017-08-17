package com.vanyle.blocks;

import java.awt.Color;

import com.vanyle.main.Explorium;

public enum Blocks {
	BlockAir(new BlockAir(Explorium.GLOBAL_SEED)),
	BlockStone(new BlockStone(Explorium.GLOBAL_SEED)),
	BlockGrass(new BlockGrass(Explorium.GLOBAL_SEED)),
	BlockSand(new BlockSand(Explorium.GLOBAL_SEED)),
	BlockWater(new BlockWater(Explorium.GLOBAL_SEED)),
	BlockDarkBrick(new BlockDarkBrick(Explorium.GLOBAL_SEED)),
	BlockCloud(new BlockCloud(Explorium.GLOBAL_SEED)),
	BlockDirt(new BlockDirt(Explorium.GLOBAL_SEED)),
	BlockTimeGoo(new BlockTimeGoo(Explorium.GLOBAL_SEED)),
	BlockTrunk(new BlockTrunk(Explorium.GLOBAL_SEED)),
	BlockLeaf(new BlockLeaf(Explorium.GLOBAL_SEED)),
	BlockVoid(new BlockVoid(Explorium.GLOBAL_SEED)),
	BlockDoor(new BlockDoor(Explorium.GLOBAL_SEED)),
	BlockLadder(new BlockLadder(Explorium.GLOBAL_SEED));
	
	private Block b;
	private int id = 0;
	private static Blocks[] v = values();
	
	Blocks(Block b){
		this.b = b;
		this.id = ordinal();
	}
	/**
	 * Get a block from his id
	 * @param id
	 * @return The Block corresponding to the id
	 */
	public static Blocks block(int id) {
		return v[id];
	}
	/**
	 * Get a block main color from his id
	 * @param id
	 * @return
	 */
	public static Color color(int id) {
		return v[id].b.mainColor;
	}
	public int id() {
		return id;
	}
	public Block b() {
		return b;
	}
}
