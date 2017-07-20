package com.vanyle.physics;

import java.awt.event.KeyEvent;
import java.util.Iterator;

import com.vanyle.data.BlockData;
import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.life.Entity;
import com.vanyle.life.Slime;
import com.vanyle.main.Explorium;
import com.vanyle.math.VMath;
import com.vanyle.procedural.ClassicGenerator;
import com.vanyle.procedural.InteriorGenerator;

public class PhysicProcessor implements Runnable{
	
	World w;
	PlayerInputManager playerinput;
	Render r;
	
	private static final double gravityX = 0;
	private static final double gravityY = 9.8;
	private static final double friction = 0.8;
	private static final int frametime = 10;
	private static final double CSIZE = Chunk.CSIZE;
	
	private static double MoveStrengh = 0.1;
	private static double JumpStrengh = 3;
	
	private static Position[] ppos = new Position[4];
	
	static {
		if(Explorium.GOD_MOD) {
			MoveStrengh = 0.3;
			JumpStrengh = 0.3;
		}
	}
	
	public PhysicProcessor(PlayerInputManager pi,World w,Render r) {
		playerinput = pi;
		this.w = w;
		this.r = r;
	}
	public static boolean hitbox(Entity obj1,Entity obj2) {
		if(
		        ((obj1.p.x() >= obj2.p.x() && obj1.p.x() <= obj2.p.x()+obj2.w) ||
		        (obj2.p.x() >= obj1.p.x() && obj2.p.x() <= obj1.p.x()+obj1.w))
		            &&
		        ((obj1.p.y() >= obj2.p.y() && obj1.p.y() <= obj2.p.y()+obj2.h) ||
		        (obj2.p.y() >= obj1.p.y() && obj2.p.y() <= obj1.p.y()+obj1.h))
		      )
		        return true;

		      return false;
	}
	public boolean eCollide(Entity e,double driftx,double drifty) {
		Position p;
		for(double i = 0;i < e.w+1;i = i + 0.2) {
			for(double j = 0;j < e.h+1;j = j + 0.2) {
				p = new Position(i+driftx, j+drifty, 0, 0);
				if(i > e.w-1) {
					p.x = e.w + driftx;
					p.overflow();
				}
				if(j > e.h-1) {
					p.y = e.h + drifty;
					p.overflow();
				}
				p.add(e.p.clone());
				if(w.getData(p.clone()) != BlockData.ID_AIR && w.getData(p.clone()) != BlockData.ID_WATER) { 
					// dont collide with fluids, cloud are not fluids
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void run() {
		int i,j,k,l;
		Position p,ptest;
		
		
		while(true) {
			
			// Handel Player moves
			
			if(playerinput.keymap[KeyEvent.VK_Z])
				if(eCollide(World.player,0,0.1) || Explorium.GOD_MOD)
					World.player.speedy -= JumpStrengh;
			if(playerinput.keymap[KeyEvent.VK_S])
				World.player.speedy += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_Q])
				World.player.speedx -= MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_D])
				World.player.speedx += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_E]) { // tries to enter a home
				if(w.worldStat != World.INSIDE_WORLD) {
					
					ppos[w.worldStat] = World.player.p.clone();
					w.worldStat = World.INSIDE_WORLD;
					
					w.setGenerator(new InteriorGenerator(0l));
					World.player.p = new Position(CSIZE+5,CSIZE+5,0,0);
					r.campos = World.player.p.clone();
					r.campos.add(new Position(-CSIZE/2,-CSIZE/4,0,0));
					w.loadAllChunk(World.player.p);
					w.regenerate();
				}
			}
			
			if(playerinput.keymap[KeyEvent.VK_A]) { // Back to overworld
				if(w.worldStat != World.REGULAR_WORLD) {
					ppos[w.worldStat] = World.player.p.clone();
					
					w.worldStat = World.REGULAR_WORLD;
					w.setGenerator(new ClassicGenerator(63l));
					
					World.player.p = ppos[World.REGULAR_WORLD].clone(); // load regular world position
					
					r.campos = World.player.p.clone();
					r.campos.add(new Position(-CSIZE/2,-CSIZE/4,0,0));
					w.loadAllChunk(new Position(0,0,0,2));
					w.regenerate();
				}
			}
			
			r.campos.converge(World.player.p,0.05,CSIZE/2,CSIZE/4);
			
			// Handel Block Physic
			for(k = 0;k < World.WORLD_DATA_SIZE;k++) {
				for(l = 0;l < World.WORLD_DATA_SIZE;l++){
					for(i = 0;i < Chunk.CSIZE;i++) {
						for(j = 0;j < Chunk.CSIZE;j++) {
							
							p = new Position(i,j,k,l);
							// 1. Liquid falls (usually. Yes, I'm thinking about you helium)
							if(w.getRelativeData(p) == BlockData.ID_WATER) {
								ptest = new Position(i,j+1,k,l);
								if(w.getRelativeData(ptest) == BlockData.ID_AIR) {
									w.setRelativeData(p, BlockData.ID_AIR);
									w.setRelativeData(ptest, BlockData.ID_WATER);
									continue;
								}
								ptest = new Position(i+1,j,k,l);
								if(w.getRelativeData(ptest) == BlockData.ID_AIR && Math.random() > 0.8) {
									w.setRelativeData(p, BlockData.ID_AIR);
									w.setRelativeData(ptest, BlockData.ID_WATER);
									continue;
								}
								ptest = new Position(i-1,j,k,l);
								if(w.getRelativeData(ptest) == BlockData.ID_AIR && Math.random() > 0.8) {
									w.setRelativeData(p, BlockData.ID_AIR);
									w.setRelativeData(ptest, BlockData.ID_WATER);
									continue;
								}
							}
							
						}
					}
				}
			}
			
			// Handel Entity Physic
			Iterator<Entity> ite = w.entitylist.iterator();
			while(ite.hasNext()) {
				Entity entity = ite.next();
				boolean col;
				entity.ai(w,this);
				
				// 1. Compute gravity.
				entity.speedx += gravityX /(frametime*frametime);
				entity.speedy += gravityY /(frametime*frametime);
				
				entity.speedx *= friction;
				entity.speedy *= friction;
				
				// 2. Apply speed
				
				if(!eCollide(entity,0,0)) {
					entity.p.x += entity.speedx;
					// if the move makes the entity collide
					col = false;
					while(eCollide(entity,0,0)) {
						entity.p.x -= entity.speedx/100;
						if(eCollide(World.player,-entity.speedx,0.1) && !eCollide(World.player,0,-1))
							entity.p.y -= 1;
						col = true;
					}
					if(col)
						entity.speedx = 0;
					col = false;
					entity.p.y += entity.speedy;
					// if the move makes the entity collide
					while(eCollide(entity,0,0)) {
						entity.p.y -= entity.speedy/100;
						col = true;
					}
					if(col)
						entity.speedy = 0;
				}else {
					entity.p.y -= 0.1; // tries to escape upwards
				}
			}
			for(i = 0;i < w.entitylist.size();i++) {
				if(!w.entitylist.get(i).alive) {
					w.entitylist.remove(i);
					i --;
				}
			}
			
			if(w.entitylist.size() < 4 && Explorium.MOBS) { // spawn a slime
				Slime s = new Slime();
				s.p = World.player.p.clone();
				s.p.x += (Math.random()-0.5) * 80;
				s.p.y += (Math.random()-0.5) * 80;
				s.p.overflow();

				if(!eCollide(s,0,0) && s.p.dist(World.player.p) > 30*30) { // spawn outside of fov
					w.entitylist.add(s);
				}
			}
			
			// Debug
			
			Render.debug[2] = "vel ("+VMath.format(World.player.speedx,3)+";"+VMath.format(World.player.speedy,3)+")";
			Render.debug[3] = "pos "+World.player.p.toString();
			Render.debug[4] = "e "+w.entitylist.size();
			
			try {
				Thread.sleep(frametime); // ~ 17ms = 60fps
			}catch(InterruptedException e) {}
		}
	}
	
}
