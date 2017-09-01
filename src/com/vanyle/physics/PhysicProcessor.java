package com.vanyle.physics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import com.vanyle.blocks.Blocks;
import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.graphics.UserEvents;
import com.vanyle.life.Entity;
import com.vanyle.life.Player;
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
	
	private static final int SpawnCap = 5; // TODO Make spawnCap depend on the world / dimension and mob type
	
	private static double MoveStrengh = 0.1;
	private static double JumpStrengh = 3;
	
	public static final int NO_COLLIDE = 0;
	public static final int REGULAR_COLLIDE = 1;
	public static final int LADDER_COLLIDE = 2;
	public static final int WATER_COLLIDE = 3;
	
	private boolean running = true;
	
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
		pi.setEvents(new UserEvents() {
			@Override
			public void mouseUp(MouseEvent me) {}
			@Override
			public void mouseMove(MouseEvent me) { // TODO add some mouse debug

			}
			@Override
			public void mouseDown(MouseEvent me) {}
			
			@Override
			public void keyup(KeyEvent ke) {}
			@Override
			public void keydown(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_F3)
					Explorium.DEBUG_INFO = !Explorium.DEBUG_INFO;
			}
		});
	}
	public void stop() { // stop thread
		running = false;
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
	public static boolean hitboxDrift(Entity obj1,Entity obj2,double driftx,double drifty) {
		if(
		        ((obj1.p.x()+driftx >= obj2.p.x() && obj1.p.x()+driftx <= obj2.p.x()+obj2.w) ||
		        (obj2.p.x() >= obj1.p.x()+driftx && obj2.p.x() <= obj1.p.x()+obj1.w+driftx))
		            &&
		        ((obj1.p.y()+drifty >= obj2.p.y() && obj1.p.y()+drifty <= obj2.p.y()+obj2.h) ||
		        (obj2.p.y() >= obj1.p.y()+drifty && obj2.p.y() <= obj1.p.y()+obj1.h+drifty))
		      )
		        return true;

		      return false;
	}
	public boolean hitboxs(Entity obj) {
		Iterator<Entity> ite = w.entitylist.iterator();
		Entity temp;
		while(ite.hasNext()) {
			temp = ite.next();
			if(temp != obj && hitbox(temp,obj))
				return true;
		}
		return false;
	}
	public boolean hitboxsDrift(Entity obj,double driftx,double drifty) {
		Iterator<Entity> ite = w.entitylist.iterator();
		Entity temp;
		while(ite.hasNext()) {
			temp = ite.next();
			if(temp != obj && hitboxDrift(temp,obj,driftx,drifty))
				return true;
		}
		return false;
	}
	public Entity hitboxWith(Entity obj) {
		Iterator<Entity> ite = w.entitylist.iterator();
		Entity temp;
		while(ite.hasNext()) {
			temp = ite.next();
			if(temp != obj && hitbox(temp,obj))
				return temp;
		}
		return null;
	}
	/**
	 * Check if e collides with the Block blockdata if this entity's position was added to driftx and drifty
	 * @param e
	 * @param driftx
	 * @param drifty
	 * @param blockdata
	 * @return
	 */
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
				if(Blocks.block(testid).b().isCollide) { 
					// dont collide with fluids, cloud are not fluids
					return REGULAR_COLLIDE;
				}
				if(testid == Blocks.BlockLadder.id()) {
					isladder = true;
				}
				if(testid == Blocks.BlockWater.id()) {
					iswater = true;
				}
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
		
		
		while(running) {
			
			// Handel Player moves
			
			if(playerinput.keymap[KeyEvent.VK_Z]) {
				int ccollide = eCollide(w.player,0,.1);
				boolean hitboxc = hitboxsDrift(w.player,0,-.3);
				int ccollide2 = eCollide(w.player,0,0);
				
				if(ccollide2 == LADDER_COLLIDE || (ccollide2 == WATER_COLLIDE && ccollide == WATER_COLLIDE))
					w.player.speedy -= MoveStrengh;
				else if((ccollide == REGULAR_COLLIDE || Explorium.GOD_MOD || hitboxc) && w.player.speedy == 0) // can only jump in air
					w.player.speedy -= JumpStrengh;
			}
			if(playerinput.keymap[KeyEvent.VK_S])
				w.player.speedy += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_Q])
				w.player.speedx -= MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_D])
				w.player.speedx += MoveStrengh;
			if(playerinput.keymap[KeyEvent.VK_E]) // tries to enter a home
				if(eCollidesWith(w.player,0,0,Blocks.BlockDoor.id()) && w.player.worldSwitchCooldown == 0)
					if(w.worldStat == World.REGULAR_WORLD)
						w.loadInsideWorld();
					else
						w.loadRegularWorld();
			
			w.campos.converge(w.player.p,0.05,CSIZE/2,CSIZE/4);
			
			// Handel Block Physic
			for(k = 0;k < World.WORLD_DATA_SIZE;k++) {
				for(l = 0;l < World.WORLD_DATA_SIZE;l++){
					for(i = 0;i < Chunk.CSIZE;i++) {
						for(j = 0;j < Chunk.CSIZE;j++) {
							
							p = new Position(i,j,k,l);
							// 1. Liquid falls (usually. Yes, I'm thinking about you helium)
							// TODO more general code about fluids, not just water
							if(w.getRelativeData(p) == Blocks.BlockWater.id()) {
								ptest = new Position(i,j+1,k,l);
								if(w.getRelativeData(ptest) == Blocks.BlockAir.id()) {
									w.setRelativeData(p, Blocks.BlockAir.id());
									w.setRelativeData(ptest, Blocks.BlockWater.id());
									continue;
								}
								ptest = new Position(i+1,j,k,l);
								if(w.getRelativeData(ptest) == Blocks.BlockAir.id() && Math.random() > 0.8) {
									w.setRelativeData(p, Blocks.BlockAir.id());
									w.setRelativeData(ptest, Blocks.BlockWater.id());
									continue;
								}
								ptest = new Position(i-1,j,k,l);
								if(w.getRelativeData(ptest) == Blocks.BlockAir.id() && Math.random() > 0.8) {
									w.setRelativeData(p, Blocks.BlockAir.id());
									w.setRelativeData(ptest, Blocks.BlockWater.id());
									continue;
								}
							}
							
						}
					}
				}
			}
			
			// Handel Entity Physic
			Iterator<Entity> ite = w.entitylist.iterator();
			Entity entityCollide = null;
			boolean upboost = false;
			
			while(ite.hasNext()) {
				Entity entity = ite.next();
				boolean col;
				entity.ai(w,this);
				
				if(entity.canDespawn(w, this) || !entity.alive) { // despawn
					entity.alive = false;
					continue;
				}
				
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
				entityCollide = hitboxWith(entity);
				upboost = false;
				int maxtry = 0;
				
				if((ccollide == NO_COLLIDE || ccollide == WATER_COLLIDE || ccollide == LADDER_COLLIDE) && entityCollide == null) {
					entity.p.x += entity.speedx;
					// if the move makes the entity collide
					col = false;
					int colw = eCollide(entity,0,0);
					while((colw == REGULAR_COLLIDE || (entityCollide=hitboxWith(entity)) != null) && maxtry < 500) {
						entity.p.x -= entity.speedx/100;
						
						// if there is ground in front of the entity and an air block above
						if(eCollide(w.player,Math.signum(entity.speedx),0) == REGULAR_COLLIDE && eCollide(w.player,0,-1) == NO_COLLIDE && !upboost) {
							entity.speedy -= 0.5; // give the entity a small push upwards to travel through stairs for example TODO make this cleaner
							upboost = true;
						}
						if(entityCollide != null && entityCollide.pushable)
							entityCollide.speedx += entity.speedx/100 * friction; // speed transfer
						col = true;
						colw = eCollide(entity,0,0);
						maxtry ++;
					}
					if(maxtry >= 500) {
						if(!(entity instanceof Player)) {
							entity.alive = false;
						}
					}
					if(col)
						entity.speedx = 0;
					col = false;
					entity.p.y += entity.speedy;
					// if the move makes the entity collide
					colw = eCollide(entity,0,0);
					maxtry = 0;
					while((colw == REGULAR_COLLIDE || (entityCollide=hitboxWith(entity)) != null) && maxtry < 500) {
						entity.p.y -= entity.speedy/100;
						if(entityCollide != null)
							entityCollide.speedy += entity.speedy/100 * friction; // speed transfer
						col = true;
						colw = eCollide(entity,0,0);
						maxtry ++;
					}
					if(maxtry >= 500) {
						if(!(entity instanceof Player)) {
							entity.alive = false;
						}
					}
					if(col)
						entity.speedy = 0;
				}else{
					entity.p.x += entity.speedx;
					entity.p.y -= 0.1; // try to escape upwards
				}
			}
			
			for(i = 0;i < w.entitylist.size();i++) { // remove dead entities
				if(!w.entitylist.get(i).alive) {
					w.entitylist.remove(i);
					i --;
				}
			}
			
			// Entity Spawning
			
			for(i = 0;i < Entity.Spawnable.length;i++) {
				if(w.entitylist.size() < SpawnCap && Explorium.MOBS) {
					try {
						Entity e = (Entity) Entity.Spawnable[i].newInstance();
						
						e.p = w.player.p.clone();
						e.p.x += (Math.random()-0.5) * 80;
						e.p.y += (Math.random()-0.5) * 80;
						e.p.overflow();
						
						if(e.canSpawn(w, this)) {
							w.entitylist.add(e);
						}
						
					} catch (InstantiationException | IllegalAccessException ex) {
						ex.printStackTrace();
					}
				}
			}
			
			if(w.player.worldSwitchCooldown > 0)
				w.player.worldSwitchCooldown --;
			
			// Debug
			if(Explorium.DEBUG_INFO) { // update infos only if required
				Render.debug[2] = "vel ("+VMath.format(w.player.speedx,3)+";"+VMath.format(w.player.speedy,3)+")";
				Render.debug[3] = "pos "+w.player.p.toString();
				Render.debug[4] = "e "+w.entitylist.size();
			}
			
			try {
				Thread.sleep(frametime); // ~ 17ms = 60fps
			}catch(InterruptedException e) {}
		}
	}
	
}
