package com.vanyle.life;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import com.vanyle.inventory.Item;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;

public class Player extends Entity{
	public int mana = 10;
	public int maxmana = 10;
	
	public List<Item> inventory = new LinkedList<Item>();
	
	public Item hat;
	public Item chest;
	public Item pants;
	public Item shoes;
	
	public Item rightHand;
	public Item leftHand;
	
	public int worldSwitchCooldown = 0;
	
	private static int fatnest = 20;
	private static int feetOffset = 2;
	private static int feetWidth = 4;
	
	public void ai(World w, PhysicProcessor pp) {}
	
	public Player() {
		texture = generateTexture(0);
	}
	
	@Override
	public BufferedImage generateTexture(double seed) {
		BufferedImage bi = new BufferedImage(32, 64, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bi.getGraphics();
		// body
		g.setColor(Color.BLUE);
		g.fillRect(16 - fatnest/2,30,fatnest,32);
		//feets
		g.fillRect(16 - fatnest/2 + feetOffset,60,feetWidth,4);
		g.fillRect(16 + fatnest/2 - feetOffset - feetWidth,60,feetWidth,4);
		// legs
		//g.fillRect(16 - fatnest/2 - 3, y, 3, height);
		
		//head
		g.setColor(Color.RED);
		g.fillRect(8,0,16,16);
		return bi;
	}

	@Override
	public boolean canDespawn(World w, PhysicProcessor pp) {
		return false;
	}

	@Override
	public boolean canSpawn(World w, PhysicProcessor pp) {
		return false;
	}
}
