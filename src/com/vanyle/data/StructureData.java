package com.vanyle.data;

public class StructureData {
	public static Structure[] list = new Structure[1];
	
	static { // TODO work in progress
		// Setup structure data
		list[0] = new Structure();
		list[0].name = "House";
		list[0].rarity = 4;
		list[0].content = "000000000"+"\n" // a house 9x9 made out of dark brick and wood (content is in base 16)
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "000000000"+"\n"
						+ "222222222"+"\n"
						+ "777777777"+"\n"
						+ "777777777"+"\n";
		list[0].ghostcontent= "555555555"+"\n" // a house 9x9 made out of dark brick and wood (content is in base 16)
							+ "599999995"+"\n"
							+ "599999995"+"\n"
							+ "599995595"+"\n"
							+ "595595595"+"\n"
							+ "595599995"+"\n"
							+ "595599995"+"\n"
							+ "595599995"+"\n"
							+ "555555555"+"\n"
							+ "222222222"+"\n"
							+ "000000000"+"\n"
							+ "000000000"+"\n";

		list[0].compile(); // compile when done defining.
		
	}
}
