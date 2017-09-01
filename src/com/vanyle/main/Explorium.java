package com.vanyle.main;

import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.graphics.Window;
import com.vanyle.menu.RenderMenu;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.procedural.ClassicGenerator;

public class Explorium {

	public static final boolean SMALL_WINDOW = false;
	public static final boolean GOD_MOD = false;
	public static final boolean MOBS = true;
	public static final boolean WIDE_FOV = false;
	public static boolean DEBUG_INFO = false;
	
	public static final long GLOBAL_SEED = (long)(Math.random()*100000l);
	
	private static PlayerInputManager pim;
	private static Window w;
	
	private static RenderMenu r_menu;
	private static Render r_classic;
	
	private static World world;
	
	private static PhysicProcessor p;
	
	public static void main(String[] args){
		pim = new PlayerInputManager();
		
		r_menu = new RenderMenu(pim);
		w = new Window(r_menu,pim);
		w.center();
	}
	
	public static void goMenu() {
		r_menu = new RenderMenu(pim);
		w.setRenderer(r_menu);
		
		p.stop();
		world = null;
		r_classic = null;
		p = null;
	}
	public static void goGame() {
		
		world = new World(new ClassicGenerator(GLOBAL_SEED));
		world.load();
		r_classic = new Render(world);
		p = new PhysicProcessor(pim,world,r_classic);
		new Thread(p).start();
		Render.debug[0] = "seed "+GLOBAL_SEED;
		
		w.setRenderer(r_classic);
		
		r_menu = null; // free memory
	}

}
