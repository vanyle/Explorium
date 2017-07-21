package com.vanyle.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayerInputManager implements PlayerInput{

	public boolean[] keymap = new boolean[16*16*16*16]; // 8 bytes of bools
	public int mouseX = 0;
	public int mouseY = 0;
	
	private UserEvents u;
	
	public void setEvents(UserEvents u) {
		this.u = u;
	}
	
	@Override
	public void keydown(KeyEvent ke) {
		keymap[ke.getKeyCode()] = true;
		if(u != null)
			u.keydown(ke);
	}
	@Override
	public void keyup(KeyEvent ke) {
		keymap[ke.getKeyCode()] = false;
		if(u != null)
			u.keyup(ke);
	}
	@Override
	public void mouseMove(MouseEvent me) {
		mouseX = me.getX();
		mouseY = me.getY();
		if(u != null)
			u.mouseUp(me);
	}

	@Override
	public void mouseDown(MouseEvent me) {
		if(u != null)
			u.mouseDown(me);
	}

	@Override
	public void mouseUp(MouseEvent me) {
		if(u != null)
			u.mouseUp(me);
	}
	
}
