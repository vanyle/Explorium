package com.vanyle.inventory;

import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;

public class Scroll {
	
	public static final int TYPE_FIRE = 0;
	public static final int TYPE_WATER = 0;
	public static final int TYPE_EARTH = 0;
	public static final int TYPE_AIR = 0;
	public static final int TYPE_LIGHT = 0;
	public static final int TYPE_DARKNESS = 0;
	public static final int TYPE_POISON = 0;
	
	public int hands_required = 1; // between 1 and 3 for multi handed species
	
	public int cooldownTime = 1000; // ms
	public int cooldown = 0;
	
	public int type = 0;
	public int mana_cost = 0;
	
	public void effect(World w,PhysicProcessor pp) {
		
	}
}
