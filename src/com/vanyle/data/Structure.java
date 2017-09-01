package com.vanyle.data;

import com.vanyle.physics.Chunk;

public class Structure {
	public String name = "Well";
	public String content =  "1111111" + "\n" 
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111";
	public String ghostcontent = "";
	public double rarity = 1; // from 1 to +infinity. freq = 1/rarity => not 0
	public int[][] raw;
	public int[][] rawghost;
	
	public double biomeRangeMinimum = -1;
	public double biomeRangeMaximum = 1;
	public int HeightRangeMaximum = -3; // as a distance from h.
	public int HeightRangeMinimum = -3;
	
	public boolean adjustHeight = true;
	
	public boolean canBeinCave = false;
	public boolean mustBeinCave = false;
	public boolean fillBelow = true;
	
	public boolean rawGen = true;
	
	public void compile() { // convert content to raw
		String[] lines = content.split("\n");
		String[] lines2 = ghostcontent.split("\n");
		raw = new int[lines.length][lines[0].length()];
		rawghost = new int[lines.length][lines[0].length()];
		int j,i;
		
		for(i = 0;i < lines.length;i++) {
			for(j = 0;j < lines[i].length();j++) {
				raw[i][j] = (int) Integer.parseInt(String.valueOf(lines[i].charAt(j)), 16);
				rawghost[i][j] = (int) Integer.parseInt(String.valueOf(lines2[i].charAt(j)), 16);
			}
		}
	}
	
	public void genScript(int i,int j,double x,double y,Chunk c) { // overrides raw gen
		
	}
	
	public int getWidth() {
		return raw[0].length;
	}
	public int getHeight() {
		return raw.length;
	}
	
	public int getData(int x,int y) {
		return raw[y][x];
	}
	public int getGhostData(int x,int y) {
		return rawghost[y][x];
	}
}
