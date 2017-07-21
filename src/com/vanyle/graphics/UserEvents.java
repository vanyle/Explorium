package com.vanyle.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface UserEvents {
	public void mouseDown(MouseEvent me);
	public void mouseUp(MouseEvent me);
	public void mouseMove(MouseEvent me);
	
	public void keydown(KeyEvent ke);
	public void keyup(KeyEvent ke);
}
