package com.vanyle.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundGenerator {
	
	private static int SAMPLE_RATE = 44100; // 16 * 1024 or ~16KHz
	
	public static final byte FADE_NONE = 0;
	public static final byte FADE_LINEAR = 1;
	public static final byte FADE_QUADRATIC = 2;
	
	public static final byte WAVE_SIN = 0;
	public static final byte WAVE_SQUARE = 1;
	public static final byte WAVE_TRIANGLE = 2;
	public static final byte WAVE_SAWTOOTH = 3;
	
	private static double BYTE_OSCI = 127.0; // max value for byte
	
	private static double no_fade(double i,int max) {
		return 1;
	}
	private static double linear_fade(double i,int max) {
		return  (max-(float)i) / max;
	}
	private static double quadratic_fade(double i,int max) {
		return (-1/Math.pow(max, 2))*Math.pow(i, 2) + 1; // (-1/ max^2) * i^2 + 1
	}
	
	
	private static double sin_wave(double i,double period) {
		return Math.sin(2.0 * Math.PI * i / period);
	}
	private static double square_wave(double i,double period) {
        return (i % period) / period < .5 ? 1 : -1;
	}
	private static double triangle_wave(double i,double period) {
		 return Math.asin(Math.sin((2 * Math.PI) * (i / period)));
	}
	private static double sawtooth_wave(double i,double period) {
		return -1 + 2 * ((i % period) / period);
	}
	/**
	 * Same as playSound but plays several frequences at the same time<br/>
	 * <code>
	 * double[] freqs = {440.0,440*1.5}; <br/>
	 * SoundGenerator.playSound(freqs,1.0,0.5,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);
	 * </code>
	 * 
	 */
	public static void playSounds(double[] freqs,double duration,double volume,byte fade,byte wave) {
		if(freqs.length == 0) {
			System.err.println("No frequences to play !");
			return;
		}
		volume = volume / freqs.length; // ease volume to avoid overflow
		
		double[][] soundData = new double[freqs.length][];
		
		for(int i = 0;i < soundData.length;i++) {
			soundData[i] = generateSoundData(freqs[i],duration,volume,fade,wave);
		}
		byte[] freqdata = new byte[soundData[0].length];
		
		for(int i = 0;i < soundData[0].length;i++) {
			for(int j = 0;j < soundData.length;j++) {
				freqdata[i] += (byte)(soundData[j][i]);
			}
		}
			
		// Play it
		try {
			final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
			SourceDataLine line = AudioSystem.getSourceDataLine(af);
			line.open(af, SAMPLE_RATE);
			line.start();
			line.write(freqdata, 0, freqdata.length);
		    line.drain();
		    line.close();
		}catch(LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Play a sound at a given frequency freq during duration (in seconds) with volume as strenght
	 * <br/><br/>
	 * <code>SoundGenerator.playSound(440.0,1.0,0.5,SoundGenerator.FADE_LINEAR,SoundGenerator.WAVE_SIN);</code><br/>
	 * Available fades : FADE_NONE, FADE_LINEAR, FADE_QUADRATIC<br/>
	 * Available waves : WAVE_SIN, WAVE_SQUARE, WAVE_TRIANGLE, WAVE_SAWTOOTH<br/>
	 */
	public static void playSound(double freq,double duration,double volume,byte fade,byte wave){
		
		double[] soundData = generateSoundData(freq,duration,volume,fade,wave);
		byte[] freqdata = new byte[soundData.length];
		
		for(int i = 0;i < soundData.length;i++) {
			freqdata[i] = (byte)soundData[i];
		}
			
		// Play it
		try {
			final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
			SourceDataLine line = AudioSystem.getSourceDataLine(af);
			line.open(af, SAMPLE_RATE);
			line.start();
			line.write(freqdata, 0, freqdata.length);
		    line.drain();
		    line.close();
		}catch(LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public static double[] generateSoundData(double freq,double duration,double volume,byte fade,byte wave) {
		double period = (double)SAMPLE_RATE / freq;
		double[] freqdata = new double[(int)(duration * SAMPLE_RATE)];
		
		// Generate the sound
		for(int i = 0; i < freqdata.length; i++) {
			double fade_data = 0;
			double wave_data = 0;
			switch(fade) {
				case FADE_NONE:
					fade_data = no_fade(i, freqdata.length);
					break;
				case FADE_LINEAR:
					fade_data = linear_fade(i, freqdata.length);
					break;
				case FADE_QUADRATIC:
					fade_data = quadratic_fade(i, freqdata.length);
					break;
			}
			switch(wave) {
				case WAVE_SIN:
					wave_data = sin_wave(i, period);
					break;
				case WAVE_SQUARE:
					wave_data = square_wave(i, period);
					break;
				case WAVE_TRIANGLE:
					wave_data = triangle_wave(i, period);
					break;
				case WAVE_SAWTOOTH:
					wave_data = sawtooth_wave(i, period);
					break;
			}
			freqdata[i] = (byte)(wave_data * BYTE_OSCI * fade_data*volume);
		}
		return freqdata;
	}
	public static void sampleRate(int sr){
		SAMPLE_RATE = sr;
	}
	public static int sampleRate(){
		return SAMPLE_RATE;
	}
}
