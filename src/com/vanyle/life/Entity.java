package com.vanyle.life;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import com.vanyle.inventory.Item;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.Position;
import com.vanyle.physics.World;

public abstract class Entity {
	
	public static final Class<?>[] Spawnable = {Slime.class,Monument.class};
	
	public Position p;
	public double speedx = 0;
	public double speedy = 0;
	public BufferedImage texture;
	
	public double w = 1.9; // size
	public double h = 3.9;
	
	public boolean pushable = true;
	
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
	
	public abstract void ai(World w,PhysicProcessor pp);
	public abstract boolean canDespawn(World w,PhysicProcessor pp);
	public abstract boolean canSpawn(World w,PhysicProcessor pp);
	public abstract BufferedImage generateTexture(double seed);
}
