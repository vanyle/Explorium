package com.vanyle.data;

public class StructureData {
	public static Structure[] list = new Structure[1];
	
	static { // TODO work in progress
		// Setup structure data
		list[0] = new Structure();
		list[0].name = "House";
		list[0].rarity = 10;
		list[9].content = "555555555"+"\n" // a house 9x9 made out of dark brick and wood (content is in base 16)
						+ "599999995"+"\n"
						+ "599999995"+"\n"
						+ "599995595"+"\n"
						+ "595595595"+"\n"
						+ "595599995"+"\n"
						+ "595599995"+"\n"
						+ "595599995"+"\n"
						+ "555555555";
		list[0].compile(); // compile when done defining.
		
	}
}
