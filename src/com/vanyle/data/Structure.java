package com.vanyle.data;

public class Structure {
	public String name = "Well";
	public String content =  "1111111" + "\n" 
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111" + "\n"
							+"1111111";
	public int rarity = 10;
	public int[][] raw;
	
	public int biomeRangeMinimum = -1;
	public int biomeRangeMaximum = 1;
	public int HeightRangeMaximum = 0; // as a distance from h.
	public int HeightRangeMinimum = 0;
	
	public boolean fillBelow = true;
	
	public void compile() { // convert content to raw
		String[] lines = content.split("\n");
		raw = new int[lines.length][lines[0].length()];
		int j;
		
		for(int i = 0;i < lines.length;i++) {
			for(j = 0;j < lines[i].length();j++) {
				raw[i][j] = (int) Integer.parseInt(String.valueOf(lines[i].charAt(j)), 16);
			}
		}
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
}
