package com.vanyle.sound;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static void beep(){
		Toolkit.getDefaultToolkit().beep();
	}
	
	private AudioInputStream ais;
	private Clip clip;
	
	public Sound(String file){
		try {
			ais = AudioSystem.getAudioInputStream(new File(file));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	public void play(){
		if(clip != null)
			clip.start();
	}
	public void time(long ms){
		if(clip != null)
			clip.setMicrosecondPosition(ms);
	}
	public void replay(){
		if(clip != null){	
			clip.flush();
			clip.start();
		}
	}
	public void stop(){
		if(clip != null)
			clip.stop();
	}
}
