package com.vanyle.life;

import java.awt.Color;

import com.vanyle.physics.Chunk;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;

public class Slime extends Entity{
	
	public Slime() {
		w = 1.9;
		h = 1.9;
		c = new Color(27, 226, 21);
	}
	
	@Override
	public void ai(World w,PhysicProcessor pp) {
		if(pp.eCollide(this,0,0.1))
			speedy -= 4;
		
		if(p.x() > World.player.p.x()) { // chase the player #stalker
			speedx -= 0.05;
		}else {
			speedx += 0.05;
		}
		if(p.dist(World.player.p) > Chunk.CSIZE*Chunk.CSIZE*4) { // despawn
			alive = false;
		}
	}
}
