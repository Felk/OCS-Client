package de.speedcube.ocsClient;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundPlayer {

	public SourceDataLine line;
	private final AudioFormat format;
	private byte[] soundData;

	public SoundPlayer(int samplerate, int samplesize, int channels, byte[] data) {
		this.soundData = data;
		format = new AudioFormat(samplerate, samplesize, channels, true, true);
		try {
			System.out.println("samplerate: "+samplerate);
			System.out.println("samplesize: "+samplesize);
			System.out.println("channels: "+channels);
			line = AudioSystem.getSourceDataLine(format);
			line.open(format, samplerate);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		line.start();
	}

	public void play() {
		line.write(soundData, 0, soundData.length);
		line.drain();
	}
	
	public void closePlayer() {
		line.stop();
	}
}