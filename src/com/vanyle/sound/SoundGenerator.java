package com.vanyle.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundGenerator {
	
	private static int SAMPLE_RATE = 44100; // 16 * 1024 or ~16KHz
	
	public static void playFrequency(int freq,double duration,double volume) throws LineUnavailableException{
		double period = (double)SAMPLE_RATE / freq;
		
		byte[] freqdata = new byte[(int)(duration * SAMPLE_RATE)];
		for(int i = 0; i < freqdata.length; i++) {
			freqdata[i] = (byte)(
					
					Math.sin(2.0 * Math.PI * i / period) * 127.0 * // classic sin wave. triangular wave for zelda style
					
					(freqdata.length-(double)i)/freqdata.length // linear fade
					*
					volume
					
					);
		}
		
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, SAMPLE_RATE);
		line.start();
		line.write(freqdata, 0, freqdata.length);
	    line.drain();
	    line.close();
	}
	public static void play(Track t,double duration) throws LineUnavailableException{
		
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, SAMPLE_RATE);
		line.start();
		
		byte[] freqdata = new byte[(int)(duration * SAMPLE_RATE)];
		
		for(int i = 0; i < freqdata.length; i++) {
			freqdata[i] = t.track(i);
		}
		
		line.write(freqdata, 0, freqdata.length);
		
	    line.drain();
	    line.close();
	}
	public static void sampleRate(int sr){
		SAMPLE_RATE = sr;
	}
	public static int sampleRate(){
		return SAMPLE_RATE;
	}
}
