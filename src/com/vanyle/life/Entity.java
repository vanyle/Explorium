package com.vanyle.life;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import com.vanyle.inventory.Item;
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
	
	public int health = 10;
	public int maxhealth = 10;
	
	public int attackCooldownTime = 1000; // ms, for basic actions too
	public int attackCooldown = 0; 
	
	// basic RPG properties
	
	public int strength = 1;
	public int agility = 1;
	public int speed = 1;
	
	public List<Item> drops = new LinkedList<Item>();
	
	public void ai(World w,PhysicProcessor pp) {
		
	}
}
