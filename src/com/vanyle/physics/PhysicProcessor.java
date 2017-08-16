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
	
	public static final int NO_COLLIDE = 0;
	public static final int REGULAR_COLLIDE = 1;
	public static final int LADDER_COLLIDE = 2;
	public static final int WATER_COLLIDE = 3;
	
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
	public boolean eCollidesWith(Entity e,double driftx,double drifty,int blockdata) {
		Position p;
		for(double i = 0;i < e.w+1;i = i + 1) {
			for(double j = 0;j < e.h+1;j = j + 1) {
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
				if(w.getData(p.clone()) == blockdata)
					return true;
				if(w.getBackgroundData(p) == blockdata)
					return true;
			}
		}
		return false;
	}
	public int eCollide(Entity e,double driftx,double drifty) {
		Position p;
		boolean iswater = false;
		boolean isladder = false;
		int testid;
		
		for(double i = 0;i < Math.floor(e.w+2);i++) {
			for(double j = 0;j < Math.floor(e.h+2);j++) {
				p = new Position(i+driftx, j+drifty, 0, 0);
				if(i > e.w) {
					p.x = e.w + driftx;
					p.overflow();
				}
				if(j > e.h) {
					p.y = e.h + drifty;
					p.overflow();
				}
				p.add(e.p.clone());
				testid = w.getData(p.clone());
				if(testid != BlockData.ID_AIR && testid != BlockData.ID_WATER && testid != BlockData.ID_LADDER) { 
					// dont collide with fluids, cloud are not fluids
					return REGULAR_COLLIDE;
				}
				if(testid == BlockData.ID_LADDER)
					isladder = true;
				if(testid == BlockData.ID_WATER)
					iswater = true;
			}
		}
		if(iswater)
			return WATER_COLLIDE;
		if(isladder)
			return LADDER_COLLIDE;
		return NO_COLLIDE;
	}
	@Override
	public void run() {
		int i,j,k,l;
		Position p,ptest;
		
		
		while(true) {
			
			// Handel Player moves
			
			if(playerinput.keymap[KeyEvent.VK_Z]) {
				int ccollide = eCollide(w.player,0,0.1);
				int ccollide2 = eCollide(w.player,0,0);
				if(ccollide == REGULAR_COLLIDE || ccollide == WATER_COLLIDE || Explorium.GOD_MOD) // can only jump in air
					w.player.speedy -= JumpStrengh;
				else if(ccollide2 == LADDER_COLLIDE)
					w.player.speedy -= MoveStrengh;
			}
			if(playerinput.keymap[KeyEvent.VK_S])
				w.player.speedy += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_Q])
				w.player.speedx -= MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_D])
				w.player.speedx += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_E]) // tries to enter a home
				if(eCollidesWith(w.player,0,0,BlockData.ID_DOOR))
					w.loadInsideWorld();
			if(playerinput.keymap[KeyEvent.VK_A]) // Back to overworld
				if(eCollidesWith(w.player,0,0,BlockData.ID_DOOR))
					w.loadRegularWorld();
			
			w.campos.converge(w.player.p,0.05,CSIZE/2,CSIZE/4);
			
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
				int ccollide = eCollide(entity,0,0);
				switch(ccollide) {
					case NO_COLLIDE: // same as regular 
					case REGULAR_COLLIDE: // apply regular gravity
						entity.speedx += gravityX /(frametime*frametime);
						entity.speedy += gravityY /(frametime*frametime);
						break;
					case WATER_COLLIDE:
						entity.speedy += gravityY /(frametime*frametime) * .08; // slow sinking
						entity.speedx += gravityX /(frametime*frametime) * .08;
						entity.speedx *= friction*0.9;
						entity.speedy *= friction*0.9;
						break;
					case LADDER_COLLIDE:
						// no gravity in ladders
						break;
				}
				
				entity.speedx *= friction;
				entity.speedy *= friction;
				
				// 2. Apply speed
				
				if(ccollide == NO_COLLIDE || ccollide == WATER_COLLIDE || ccollide == LADDER_COLLIDE) {
					entity.p.x += entity.speedx;
					// if the move makes the entity collide
					col = false;
					int colw = eCollide(entity,0,0);
					while(colw == REGULAR_COLLIDE) {
						Render.debug[3] = "pos "+w.player.p.toString();
						entity.p.x -= entity.speedx/100;
						if(eCollide(w.player,-entity.speedx,0.1) == REGULAR_COLLIDE && eCollide(w.player,0,-1) == NO_COLLIDE)
							entity.p.y -= 1;
						col = true;
						colw = eCollide(entity,0,0);
					}
					if(col)
						entity.speedx = 0;
					col = false;
					entity.p.y += entity.speedy;
					// if the move makes the entity collide
					colw = eCollide(entity,0,0);
					while(colw == REGULAR_COLLIDE) {
						Render.debug[3] = "pos "+w.player.p.toString();
						entity.p.y -= entity.speedy/100;
						col = true;
						colw = eCollide(entity,0,0);
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
			
			if(w.entitylist.size() < 4 && Explorium.MOBS && w.worldStat == World.REGULAR_WORLD) { // spawn a slime
				Slime s = new Slime();
				s.p = w.player.p.clone();
				s.p.x += (Math.random()-0.5) * 80;
				s.p.y += (Math.random()-0.5) * 80;
				s.p.overflow();

				if(eCollide(s,0,0) != NO_COLLIDE && s.p.dist(w.player.p) > 30*30) { // spawn outside of fov
					w.entitylist.add(s);
				}
			}
			
			// Debug
			
			Render.debug[2] = "vel ("+VMath.format(w.player.speedx,3)+";"+VMath.format(w.player.speedy,3)+")";
			Render.debug[3] = "pos "+w.player.p.toString();
			Render.debug[4] = "e "+w.entitylist.size();
			
			try {
				Thread.sleep(frametime); // ~ 17ms = 60fps
			}catch(InterruptedException e) {}
		}
	}
	
}
