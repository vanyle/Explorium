
package com.vanyle.sound;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// TODO create methods with higher level than writeDoubleToBuffer
public class WavCreator {
	
	private double duration = 0;
	List<Double> buffer = new LinkedList<Double>();
	
	public WavCreator() {
		
	}
	public void writeDoubleToBuffer(double d) {
		buffer.add(d);
		duration += 1./SoundGenerator.sampleRate();
	}
	
	// generate a one channel wave file TODO more channel version
	public void generateWavFile(String path) throws Exception {
		long numFrames = (long)(duration * SoundGenerator.sampleRate());
		WavFile wfile = WavFile.newWavFile(new File(path), 1, numFrames, 16, SoundGenerator.sampleRate());
        double[][] tempbuffer = new double[1][100];
        Iterator<Double> ite = buffer.iterator();
        
        // Initialise a local frame counter
        while(ite.hasNext()) {
	        int i = 0;
	        int toWrite = (wfile.getFramesRemaining() > tempbuffer[0].length) ? tempbuffer[0].length : (int) wfile.getFramesRemaining();
	        // Loop until all frames written
	    	while (ite.hasNext() && (i < toWrite)){
	    		tempbuffer[0][i] = (double) ite.next();
	    		i++;
	    	}
	        wfile.writeFrames(tempbuffer, toWrite);
	        if(wfile.getFramesRemaining() == 0)
	        	break;
        }
        
        // Close the wavFile
        wfile.close();
	}
}
