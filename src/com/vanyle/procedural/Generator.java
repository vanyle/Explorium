package com.vanyle.procedural;

import java.util.Random;

import com.vanyle.physics.Chunk;
import com.vanyle.physics.Position;

public abstract class Generator {
	protected double seed = Math.random();
	protected static final double CSIZE = Chunk.CSIZE;
	
	public Generator(long seed) {
		Random rnd = new Random(seed);
		int c = (int) (rnd.nextDouble()*30);
		for(int i = 0;i < c;i++)
			rnd.nextDouble();
		this.seed = rnd.nextDouble();
	}
	public double seed() {
		return seed;
	}
	/**
	 * Generate a chunk from the position of a block located inside the chunk.
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract Chunk generate(Position p);
}
