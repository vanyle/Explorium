package com.vanyle.main;

import javax.sound.sampled.LineUnavailableException;

import com.vanyle.graphics.PlayerInputManager;
import com.vanyle.graphics.Render;
import com.vanyle.graphics.Window;
import com.vanyle.physics.Generator;
import com.vanyle.physics.PhysicProcessor;
import com.vanyle.physics.World;
import com.vanyle.sound.SoundGenerator;

public class Explorium2 {

	public static final boolean SMALL_WINDOW = true;
	public static final boolean GOD_MOD = true;
	public static final boolean MOBS = false;
	public static final boolean WIDE_FOV = false;
	
	public static void main(String[] args) {
		
		PlayerInputManager pim = new PlayerInputManager();
		World world = new World(new Generator(63l));
		world.load();
		
		Render r = new Render(world);
		Window w = new Window(r,pim);
		w.center();
		
		PhysicProcessor p = new PhysicProcessor(pim,world,r);
		new Thread(p).start();
		
		try {
			SoundGenerator.playFrequency(440, 1, 1); // LA
			SoundGenerator.playFrequency(660, 1, 1); // *1.5
			SoundGenerator.playFrequency(330, 1, 1); // Dominante down
			SoundGenerator.playFrequency(880, 1, 1); // LA up
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
