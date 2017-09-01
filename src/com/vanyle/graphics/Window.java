package com.vanyle.graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.vanyle.main.Explorium;

public class Window extends JFrame implements WindowListener, KeyListener, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	private Panel p;
	private PlayerInput pi;
	
	public static int WIDTH = (int)(1280);
	public static int HEIGHT = (int)(720);
	
	static {
		if(!Explorium.SMALL_WINDOW) {
			WIDTH *= 1.5;
			HEIGHT *= 1.5;
		}
	}
	
	public Window(Renderer r,PlayerInput pi) {
		setTitle("Explorium2");
		if(!Explorium.SMALL_WINDOW) {
			setUndecorated(true);
		}
		setVisible(true);
		setResizable(false);
		
		this.pi = pi;
		
		p = new Panel(r);
		setContentPane(p);
		
		addWindowListener(this);
		addKeyListener(this);
		p.addMouseListener(this);
		p.addMouseMotionListener(this);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_4BYTE_ABGR), new Point(0, 0), "blank cursor"));
		
		p.startRender();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	public PlayerInput getPlayerInput() {
		return pi;
	}
	public static int[] screensize(){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new int[]{d.width,d.height};
	}
	public void center(){
		int[] sSize = screensize();
		size(WIDTH, HEIGHT);
		position((sSize[0]-WIDTH)/2, (sSize[1]-HEIGHT)/2);
	}
	public void position(int x,int y){
		setLocation(x, y);
	}
	public void size(int x,int y){
		setSize(x, y);
	}
	public void setRenderer(Renderer r) {
		p.setRenderer(r);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {	}
	@Override
	public void mousePressed(MouseEvent e) {
		pi.mouseDown(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		pi.mouseUp(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		pi.mouseMove(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		pi.mouseMove(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		pi.keydown(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		pi.keyup(e);
	}
	@Override
	public void keyTyped(KeyEvent e) {}

}
