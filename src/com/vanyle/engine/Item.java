package com.vanyle.engine;

public abstract class Item {
	protected int durability = 100;
	protected String lore = "Only obtainable with bugs";
	protected String name = "An object";
	protected int weight = 1;
	protected byte rarity = 0;
	protected int productionDifficulty = 0;
	protected int sharpness = 0; // mesure of sharpness
	protected int size = 1;
	protected int fragility = 0;
	
	public static final byte RARITY_COMMON = 0; // white
	public static final byte RARITY_UNCOMMON = 1; // green
	public static final byte RARITY_RARE = 2; // blue
	public static final byte RARITY_LEGENDARY = 3; // orange
	public static final byte RARITY_MYTHIC = 4; // purple
	
	public int getDurability(){
		return durability;
	}
	public String getLore(){
		return lore;
	}
	public String getName(){
		return name;
	}
	public int getWeight(){
		return weight;
	}
	public byte getRarity(){
		return rarity;
	}
	public int getCost(){
		return productionDifficulty*(rarity+1)*(rarity+1);
	}
	public int getAttackValue(){
		return (sharpness+1) * (int)Math.min(rarity / 1.5f,1) * weight;
	}
	public int protectionAmout(){
		return (int)Math.min(rarity / 1.5f,1)*2*size / (sharpness+1);
	}
	public int getFragility(){
		return fragility;
	}
}
