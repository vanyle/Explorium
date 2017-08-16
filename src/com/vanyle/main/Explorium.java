package com.vanyle.main;

import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.graphics.Window;
import com.vanyle.menu.RenderMenu;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.procedural.ClassicGenerator;
import com.vanyle.procedural.TextureGenerator;

public class Explorium {

	public static final boolean SMALL_WINDOW = false;
	public static final boolean GOD_MOD = false;
	public static final boolean MOBS = true;
	public static final boolean WIDE_FOV = false;
	
	public static final long GLOBAL_SEED = (long)(Math.random()*100000l);
	
	private static PlayerInputManager pim;
	private static Window w;
	
	private static RenderMenu r_menu;
	private static Render r_classic;
	
	private static World world;
	
	private static PhysicProcessor p;
	
	public static void main(String[] args) {
		
		TextureGenerator.setup();
		
		pim = new PlayerInputManager();
		
		r_menu = new RenderMenu(pim);
		w = new Window(r_menu,pim);
		w.center();
		
		//PhysicProcessor p = new PhysicProcessor(pim,world,r);
		//new Thread(p).start();
		
		// Music demo
		/*
		double[] freq = {440.0,440*1.5,440*1.2};
		SoundGenerator.playSounds(freq2,2.0,0.5,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);
		SoundGenerator.playSound(440,1.0,1.0,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);
		*/
	}
	
	public static void goMenu() {
		
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
