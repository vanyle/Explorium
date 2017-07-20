package com.vanyle.main;

import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.graphics.Window;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.procedural.ClassicGenerator;

public class Explorium2 {

	public static final boolean SMALL_WINDOW = true;
	public static final boolean GOD_MOD = false;
	public static final boolean MOBS = false;
	public static final boolean WIDE_FOV = true;
	
	public static void main(String[] args) throws InterruptedException {
		
		PlayerInputManager pim = new PlayerInputManager();
		World world = new World(new ClassicGenerator(63l));
		world.load();
		
		Render r = new Render(world);
		Window w = new Window(r,pim);
		w.center();
		
		PhysicProcessor p = new PhysicProcessor(pim,world,r);
		new Thread(p).start();
		
		/*
		double[] freq = {440.0,440*1.5,440*1.2};
		SoundGenerator.playSounds(freq2,2.0,0.5,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);
		SoundGenerator.playSound(440,1.0,1.0,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);
		*/
	}

}
