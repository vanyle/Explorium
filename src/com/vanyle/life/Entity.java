package com.vanyle.life;

import java.awt.Color;

import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.Position;
import com.vanyle.physics.World;

public class Entity {
	public Position p;
	public double speedx = 0;
	public double speedy = 0;
	
	public double w = 1.9; // size
	public double h = 3.9;
	
	public boolean alive = true;
	
	public boolean gravity = true;
	public boolean bcollision = true;
	public boolean ecollision = true;
	
	public Color c = Color.red;
	
	public void ai(World w,PhysicProcessor pp) {
		
	}
}
