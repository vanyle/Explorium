package com.vanyle.misc;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Tools {
	public static BufferedImage screenshot(){
		try {
			return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		} catch (HeadlessException e) {
			System.err.println("I dont have a screen!");
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String dispArray(Object[] a){
		String s = "[";
		for(int i = 0;i < a.length;i++){
			if(a[i] instanceof Object[])
				s += dispArray((Object[])a[i]);
			else
				s += a[i].toString();
			
			if(i != a.length-1)
				s += ",";
		}
		s += "]";
		
		return s;
	}
	public static String dispBytes(byte[] a){
		String s = "[";
		for(int i = 0;i < a.length;i++){
			s += a[i];
			if(i != a.length-1)
				s += ",";
		}
		s += "]";
		
		return s;
	}
	public static void movemouse(int x,int y){
		try {
			new Robot().mouseMove(x, y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void mouseclick(){
		try {
			new Robot().mousePress(MouseEvent.BUTTON1);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
