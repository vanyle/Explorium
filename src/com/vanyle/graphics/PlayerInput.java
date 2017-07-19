package com.vanyle.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface PlayerInput {
	public void keydown(KeyEvent ke);
	public void keyup(KeyEvent ke);
	
	public void mouseMove(MouseEvent me);
	public void mouseDown(MouseEvent me);
	public void mouseUp(MouseEvent me);
}
