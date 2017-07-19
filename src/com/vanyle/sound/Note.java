package com.vanyle.sound;

public enum Note {
	
	REST, A4, A4$, B4, C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$, A5;
	
    public double freq = 0;

    Note() {
        int n = ordinal();
        if (n > 0) { // not rest
            double exp = ((double) n - 1) / 12d;
            freq = 440d * Math.pow(2d, exp);
        }
    }

    public double freq() {
        return freq;
    }
}
