package com.vanyle.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayerInputManager implements PlayerInput{

	public boolean[] keymap = new boolean[16*16*16*16]; // 8 bytes of bools
	
	@Override
	public void keydown(KeyEvent ke) {
		keymap[ke.getKeyCode()] = true;
	}
	@Override
	public void keyup(KeyEvent ke) {
		keymap[ke.getKeyCode()] = false;
	}
	@Override
	public void mouseMove(MouseEvent me) {
		
	}

	@Override
	public void mouseDown(MouseEvent me) {
		
	}

	@Override
	public void mouseUp(MouseEvent me) {
		
	}
	
}
