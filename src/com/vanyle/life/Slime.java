package com.vanyle.life;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.SimplexNoise;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.procedural.TextureGenerator;

public class Slime extends Entity{
	
	public static final double speed = 0.05;
	public static final double jumpStrength = 1;
	private boolean direction = false;
	
	public Slime() {
		w = 1.9;
		h = 1.9;
		direction = Math.random() > 0.5;
		c = new Color(27, 226, 21);
		
		texture = generateTexture(0);
	}
	
	@Override
	public void ai(World w,PhysicProcessor pp) {
		if(pp.eCollide(this,0,0.1) == PhysicProcessor.REGULAR_COLLIDE)
			speedy -= jumpStrength;
		speedx = direction ? speed : -speed;
	}

	@Override
	public BufferedImage generateTexture(double seed) {
		BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = new Color(0,255,0);
				if(i < 5 || j < 5 || i > 26 || j > 26)
					c = new Color(0,128,0);
				else {
					c = TextureGenerator.trick(c, ((i+j) % 5)*30 + SimplexNoise.noise(i/10f + 7.3, j/10f + 5.6, seed + 0.1f)*100);
				}
				bi.setRGB(i, j, c.getRGB());
			}
		}
		
		return bi;
	}

	@Override
	public boolean canDespawn(World w, PhysicProcessor pp) {
		return p.dist(w.player.p) > Chunk.CSIZE*Chunk.CSIZE*4 || w.worldStat != World.REGULAR_WORLD;
	}

	@Override
	public boolean canSpawn(World w, PhysicProcessor pp) {
		return pp.eCollide(this,0,0) == PhysicProcessor.NO_COLLIDE &&
				pp.eCollide(this,0,2) == PhysicProcessor.REGULAR_COLLIDE &&
				!pp.hitboxs(this) &&
				w.worldStat == World.REGULAR_WORLD &&
				p.dist(w.player.p) > 30*30;
	}
}
