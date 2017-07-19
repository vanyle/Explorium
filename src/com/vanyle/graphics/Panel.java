package com.vanyle.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private Renderer r;
	
	public Panel(Renderer r) {
		this.r = r;
	}
	public void setRenderer(Renderer r) {
		this.r = r;
	}
	public void startRender() {
		new Thread(this).start();
	}
	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(17); // ~ 60 fps
			}catch(InterruptedException e) {}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		r.render(g); // TODO FPS count
	}
}
