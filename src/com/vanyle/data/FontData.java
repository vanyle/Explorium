package com.vanyle.data;

import java.awt.Graphics;

/**
 * Stores a font as int arrays
 * @author vanyle
 *
 */
public class FontData {
	public static int[][] CHAR_SPACE = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1}	
	};
	public static int[][] CHAR_MAJ_A = { // 5x6
			{1,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,0,0,0,0},
			{0,1,1,1,0},
			{0,1,1,1,0}
	};
	public static int[][] CHAR_MAJ_B = { // 5x6
			{0,0,0,1},
			{0,1,1,0},
			{0,0,0,1},
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,1}
	};
	public static int[][] CHAR_MAJ_C = { // 5x6
			{1,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,1},
			{0,1,1,1,1},
			{0,1,1,1,0},
			{1,0,0,0,1}
	};
	public static int[][] CHAR_MAJ_D = { // 5x6
			{0,0,0,1},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,1}
	};
	public static int[][] CHAR_MAJ_E = { // 5x6
			{0,0,0,0},
			{0,1,1,1},
			{0,0,0,0},
			{0,1,1,1},
			{0,1,1,1},
			{0,0,0,0}
	};
	public static int[][] CHAR_MAJ_F = { // 5x6
			{0,0,0,0},
			{0,1,1,1},
			{0,0,0,0},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1}
	};
	public static int[][] CHAR_MAJ_G = {
			{1,0,0,0},
			{0,1,1,1},
			{0,1,0,0},
			{0,1,1,0},
			{0,1,1,0},
			{1,0,0,1}
	};
	public static int[][] CHAR_MAJ_H = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,0,0,0,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0}	
	};
	public static int[][] CHAR_MAJ_I = {
			{0,0,0},
			{1,0,1},
			{1,0,1},
			{1,0,1},
			{1,0,1},
			{0,0,0}	
	};
	public static int[][] CHAR_MAJ_J = {
			{1,0,0,0,0},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{0,1,1,0,1},
			{1,0,0,1,1}	
	};
	public static int[][] CHAR_MAJ_K = {
			{0,1,1,0},
			{0,1,0,1},
			{0,0,1,1},
			{0,1,0,1},
			{0,1,1,0},
			{0,1,1,0}	
	};
	public static int[][] CHAR_MAJ_L = {
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MAJ_M = {
			{0,1,1,1,0},
			{0,0,1,0,0},
			{0,1,0,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0}	
	};
	public static int[][] CHAR_MAJ_N = {
			{0,1,1,0},
			{0,0,1,0},
			{0,1,0,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0}	
	};
	public static int[][] CHAR_MAJ_O = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,0,0,1}	
	};
	public static int[][] CHAR_MAJ_P = {
			{0,0,0,0},
			{0,1,1,0},
			{0,0,0,0},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1}	
	};
	public static int[][] CHAR_MAJ_Q = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,0,1,0},
			{0,1,1,0,1},
			{1,0,0,1,0}	
	};
	public static int[][] CHAR_MAJ_R = {
			{0,0,0,1},
			{0,1,1,0},
			{0,0,0,1},
			{0,1,0,1},
			{0,1,1,0},
			{0,1,1,0}	
	};
	public static int[][] CHAR_MAJ_S = {
			{0,0,0,0},
			{0,1,1,1},
			{0,0,0,0},
			{1,1,1,0},
			{1,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MAJ_T = {
			{0,0,0,0,0},
			{1,1,0,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MAJ_U = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,0,0,1}	
	};
	public static int[][] CHAR_MAJ_V = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,1,0,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MAJ_W = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,0,1,0},
			{0,1,0,1,0},
			{1,0,1,0,1}	
	};
	public static int[][] CHAR_MAJ_X = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,1,0,1},
			{1,1,0,1,1},
			{1,0,1,0,1},
			{0,1,1,1,0}	
	};
	public static int[][] CHAR_MAJ_Y = {
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,1,0,1},
			{1,1,0,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MAJ_Z = {
			{0,0,0,0,0},
			{1,1,1,1,0},
			{1,1,1,0,1},
			{1,1,0,1,1},
			{1,0,1,1,1},
			{0,0,0,0,0}	
	};
	public static int[][] CHAR_MIN_A = {
			{1,1,1,1},
			{0,0,0,1},
			{1,1,1,0},
			{0,0,0,0},
			{0,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_B = {
			{0,1,1,1},
			{0,1,1,1},
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_C = {
			{1,1,1,1},
			{1,0,0,0},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{1,0,0,0}	
	};
	public static int[][] CHAR_MIN_D = {
			{1,1,1,0},
			{1,1,1,0},
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_E = {
			{1,1,1,1},
			{0,0,0,0},
			{0,1,1,0},
			{0,0,0,0},
			{0,1,1,1},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_F = {
			{1,1,1},
			{0,0,0},
			{0,1,1},
			{0,0,1},
			{0,1,1},
			{0,1,1}	
	};
	public static int[][] CHAR_MIN_G = {
			{1,1,1,1},
			{0,0,0,0},
			{0,1,1,1},
			{0,1,0,0},
			{0,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_H = {
			{1,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0}	
	};
	public static int[][] CHAR_MIN_I = {
			{0},
			{1},
			{0},
			{0},
			{0},
			{0}	
	};
	public static int[][] CHAR_MIN_J = {
			{1,0},
			{1,1},
			{1,0},
			{1,0},
			{1,0},
			{0,0}	
	};
	public static int[][] CHAR_MIN_K = {
			{1,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,0},
			{0,0,0,1},
			{0,1,1,0}	
	};
	public static int[][] CHAR_MIN_L = {
			{0},
			{0},
			{0},
			{0},
			{0},
			{0}	
	};
	public static int[][] CHAR_MIN_M = {
			{1,1,1,1,1},
			{1,0,1,0,1},
			{0,1,0,1,0},
			{0,1,0,1,0},
			{0,1,0,1,0},
			{0,1,0,1,0}	
	};	
	public static int[][] CHAR_MIN_N = {
			{1,1,1,1},
			{1,0,0,1},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0}	
	};	
	public static int[][] CHAR_MIN_O = {
			{1,1,1,1},
			{1,0,0,1},
			{0,1,1,0},
			{0,1,1,0},
			{0,1,1,0},
			{1,0,0,1}	
	};	
	public static int[][] CHAR_MIN_P = {
			{1,1,1,1},
			{0,0,0,0},
			{0,1,1,0},
			{0,0,0,0},
			{0,1,1,1},
			{0,1,1,1}	
	};	
	public static int[][] CHAR_MIN_Q = {
			{1,1,1,1},
			{0,0,0,0},
			{0,1,1,0},
			{0,0,0,0},
			{1,1,1,0},
			{1,1,1,0}	
	};
	public static int[][] CHAR_MIN_R = {
			{1,1,1,1},
			{1,0,0,0},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1},
			{0,1,1,1}	
	};
	public static int[][] CHAR_MIN_S = {
			{1,1,1},
			{0,0,0},
			{0,1,1},
			{0,0,0},
			{1,1,0},
			{0,0,0}	
	};
	public static int[][] CHAR_MIN_T = {
			{1,1,1},
			{0,1,1},
			{0,0,0},
			{0,1,1},
			{0,1,1},
			{0,0,0}	
	};
	public static int[][] CHAR_MIN_U = {
			{1,1,1,1,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,0,0,1}	
	};
	public static int[][] CHAR_MIN_V = {
			{1,1,1,1,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,1,0,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MIN_W = {
			{1,1,1,1,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{0,1,0,1,0},
			{0,1,0,1,0},
			{1,0,1,0,1}	
	};
	public static int[][] CHAR_MIN_X = {
			{1,1,1,1,1},
			{0,1,1,1,0},
			{1,0,1,0,1},
			{1,1,0,1,1},
			{1,0,1,0,1},
			{0,1,1,1,0}	
	};
	public static int[][] CHAR_MIN_Y = {
			{1,1,1,1},
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,0},
			{1,1,1,0},
			{0,0,0,0}	
	};
	public static int[][] CHAR_MIN_Z = {
			{1,1,1,1,1},
			{0,0,0,0,0},
			{1,1,1,0,1},
			{1,1,0,1,1},
			{1,0,1,1,1},
			{0,0,0,0,0}	
	};
	public static int[][] CHAR_NUM_0 = {
			{1,0,0,1,1},
			{0,1,1,0,1},
			{0,1,0,0,1},
			{0,0,1,0,1},
			{0,1,1,0,1},
			{1,0,0,1,1}	
	};
	public static int[][] CHAR_NUM_1 = {
			{1,1,0,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1}	
	};
	public static int[][] CHAR_NUM_2 = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{1,1,1,0,1},
			{1,0,0,1,1},
			{0,1,1,1,1},
			{0,0,0,0,0}	
	};
	public static int[][] CHAR_NUM_3 = {
			{1,0,0,0,1},
			{1,1,1,1,0},
			{1,0,0,0,1},
			{1,1,1,1,0},
			{1,1,1,1,0},
			{1,0,0,0,1}
	};
	public static int[][] CHAR_NUM_4 = {
			{0,1,1,0,1},
			{0,1,1,0,1},
			{0,0,0,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1}
	};
	public static int[][] CHAR_NUM_5 = {
			{0,0,0,0,1},
			{0,1,1,1,1},
			{0,0,0,0,1},
			{1,1,1,1,0},
			{1,1,1,1,0},
			{0,0,0,0,1}
	};
	public static int[][] CHAR_NUM_6 = {
			{1,0,0,0,1},
			{0,1,1,1,1},
			{0,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,0,0,1}
	};
	public static int[][] CHAR_NUM_7 = {
			{1,0,0,0,0},
			{1,1,1,1,0},
			{1,1,1,1,0},
			{1,1,1,0,1},
			{1,1,1,0,1},
			{1,1,1,0,1}
	};
	public static int[][] CHAR_NUM_8 = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{1,0,0,0,1},
			{0,1,1,1,0},
			{0,1,1,1,0},
			{1,0,0,0,1}
	};
	public static int[][] CHAR_NUM_9 = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{1,0,0,0,0},
			{1,1,1,1,0},
			{1,1,1,0,1},
			{1,1,1,0,1}
	};
	
	public static int[][] CHAR_MISC_DOT = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,0,0,1},
			{1,1,0,0,1}	
	};
	public static int[][] CHAR_MISC_COMMA = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MISC_SEMICOLON = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MISC_COLON = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,1,1,1}	
	};
	public static int[][] CHAR_MISC_OPEN_BR = {
			{1,0,1},
			{0,1,1},
			{0,1,1},
			{0,1,1},
			{0,1,1},
			{1,0,1}	
	};
	public static int[][] CHAR_MISC_CLOSE_BR = {
			{1,0,1},
			{1,1,0},
			{1,1,0},
			{1,1,0},
			{1,1,0},
			{1,0,1}	
	};
	public static int[][] CHAR_MISC_DASH = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,1,1,1,1},
			{1,0,0,0,1},
			{1,1,1,1,1},
			{1,1,1,1,1}	
	};
	public static int[][] CHAR_MISC_PLUS = {
			{1,1,1,1,1},
			{1,1,0,1,1},
			{1,1,0,1,1},
			{0,0,0,0,0},
			{1,1,0,1,1},
			{1,1,0,1,1}	
	};
	public static int[][] CHAR_MISC_QUESTION_MARK = {
			{1,0,0,0,1},
			{0,1,1,1,0},
			{1,1,1,0,1},
			{1,1,0,1,1},
			{1,1,1,1,1},
			{1,1,0,1,1}		
	};
	public static int[][] CHAR_MISC_EXCLAMATION_MARK = {
			{1,0,1},
			{1,0,1},
			{1,0,1},
			{1,0,1},
			{1,1,1},
			{1,0,1}	
	};
	public static int[][] CHAR_MISC_EQUAL = {
			{1,1,1,1,1},
			{1,1,1,1,1},
			{0,0,0,0,0},
			{1,1,1,1,1},
			{0,0,0,0,0},
			{1,1,1,1,1}	
	};
	public static int length(String s,int size) {
		int l = 0;
		for(int i = 0;i < s.length();i++) {
			l += size*(charToMap(s.charAt(i))[0].length + 2);
		}
		l -= 2*size;
		return l;
	}
	public static void drawString(String s,int x,int y,Graphics g,int size) {
		int cx = x;
		for(int i = 0;i < s.length();i++) {
			int[][] charmap = charToMap(s.charAt(i));
			for(int j = 0;j < charmap.length;j++) {
				for(int k = 0;k < charmap[j].length;k++) {
					if(charmap[j][k] == 0)
						g.fillRect(cx+(k*size), y+(j*size), size, size);
				}
			}
			cx += (charmap[0].length+2)*size;
		}
	}
	public static int[][] charToMap(char c){
		c = (c+"").charAt(0);
		
		switch(c) {
			case 'A':
				return CHAR_MAJ_A;
			case 'B':
				return CHAR_MAJ_B;
			case 'C':
				return CHAR_MAJ_C;
			case 'D':
				return CHAR_MAJ_D;
			case 'E':
				return CHAR_MAJ_E;
			case 'F':
				return CHAR_MAJ_F;
			case 'G':
				return CHAR_MAJ_G;
			case 'H':
				return CHAR_MAJ_H;
			case 'I':
				return CHAR_MAJ_I;
			case 'J':
				return CHAR_MAJ_J;
			case 'K':
				return CHAR_MAJ_K;
			case 'L':
				return CHAR_MAJ_L;
			case 'M':
				return CHAR_MAJ_M;
			case 'N':
				return CHAR_MAJ_N;
			case 'O':
				return CHAR_MAJ_O;
			case 'P':
				return CHAR_MAJ_P;
			case 'Q':
				return CHAR_MAJ_Q;
			case 'R':
				return CHAR_MAJ_R;
			case 'S':
				return CHAR_MAJ_S;
			case 'T':
				return CHAR_MAJ_T;
			case 'U':
				return CHAR_MAJ_U;
			case 'V':
				return CHAR_MAJ_V;
			case 'W':
				return CHAR_MAJ_W;
			case 'X':
				return CHAR_MAJ_X;
			case 'Y':
				return CHAR_MAJ_Y;
			case 'Z':
				return CHAR_MAJ_Z;
			case 'a':
				return CHAR_MIN_A;
			case 'b':
				return CHAR_MIN_B;
			case 'c':
				return CHAR_MIN_C;
			case 'd':
				return CHAR_MIN_D;
			case 'e':
				return CHAR_MIN_E;
			case 'f':
				return CHAR_MIN_F;
			case 'g':
				return CHAR_MIN_G;
			case 'h':
				return CHAR_MIN_H;
			case 'i':
				return CHAR_MIN_I;
			case 'j':
				return CHAR_MIN_J;
			case 'k':
				return CHAR_MIN_K;
			case 'l':
				return CHAR_MIN_L;
			case 'm':
				return CHAR_MIN_M;
			case 'n':
				return CHAR_MIN_N;
			case 'o':
				return CHAR_MIN_O;
			case 'p':
				return CHAR_MIN_P;
			case 'q':
				return CHAR_MIN_Q;
			case 'r':
				return CHAR_MIN_R;
			case 's':
				return CHAR_MIN_S;
			case 't':
				return CHAR_MIN_T;
			case 'u':
				return CHAR_MIN_U;
			case 'v':
				return CHAR_MIN_V;
			case 'w':
				return CHAR_MIN_W;
			case 'x':
				return CHAR_MIN_X;
			case 'y':
				return CHAR_MIN_Y;
			case 'z':
				return CHAR_MIN_Z;
			case '0':
				return CHAR_NUM_0;
			case '1':
				return CHAR_NUM_1;
			case '2':
				return CHAR_NUM_2;
			case '3':
				return CHAR_NUM_3;
			case '4':
				return CHAR_NUM_4;
			case '5':
				return CHAR_NUM_5;
			case '6':
				return CHAR_NUM_6;
			case '7':
				return CHAR_NUM_7;
			case '8':
				return CHAR_NUM_8;
			case '9':
				return CHAR_NUM_9;
			case '.':
				return CHAR_MISC_DOT;
			case ';':
				return CHAR_MISC_SEMICOLON;
			case ':':
				return CHAR_MISC_COLON;
			case ',':
				return CHAR_MISC_COMMA;
			case '(':
				return CHAR_MISC_OPEN_BR;
			case ')':
				return CHAR_MISC_CLOSE_BR;
			case '-':
				return CHAR_MISC_DASH;
			case '+':
				return CHAR_MISC_PLUS;
			case '!':
				return CHAR_MISC_EXCLAMATION_MARK;
			case '?':
				return CHAR_MISC_QUESTION_MARK;
			case '=':
				return CHAR_MISC_EQUAL;
			default:
				return CHAR_SPACE;
		}
	}
}