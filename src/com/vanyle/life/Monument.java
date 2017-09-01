package com.vanyle.life;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vanyle.math.SimplexNoise;
import com.vanyle.physics.Chunk;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.procedural.TextureGenerator;

public class Monument extends Entity{
	
	public Monument(){
		w = 2;
		h = 6;
		texture = generateTexture(0);
	}

	@Override
	public void ai(World w, PhysicProcessor pp) {
		// is not moving
	}

	@Override
	public boolean canDespawn(World w, PhysicProcessor pp) {
		return p.dist(w.player.p) > Chunk.CSIZE*Chunk.CSIZE*4 || w.worldStat != World.REGULAR_WORLD;
	}

	@Override
	public boolean canSpawn(World w, PhysicProcessor pp) {
		return pp.eCollide(this,0,0) == PhysicProcessor.NO_COLLIDE &&
				pp.eCollide(this,0,2) == PhysicProcessor.REGULAR_COLLIDE &&
				w.worldStat == World.REGULAR_WORLD &&
				!pp.hitboxs(this) &&
				p.dist(w.player.p) > 30*30;
	}

	@Override
	public BufferedImage generateTexture(double seed) {
		BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int i = 0;i < bi.getWidth();i++) {
			for(int j = 0;j < bi.getHeight();j++) {
				c = new Color(0,0,0);
				c = TextureGenerator.trick(c, SimplexNoise.noise(i/10f + 7.3, j/10f + 5.6, seed + 0.1f)*10);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		
		return bi;
	}
}
