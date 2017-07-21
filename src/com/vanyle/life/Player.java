package com.vanyle.life;

import java.util.LinkedList;
import java.util.List;

import com.vanyle.inventory.Item;

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
}
