package de.speedcube.ocsClient;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioFile {

	private String filename;
	private Clip clip;

	public AudioFile(String filename) {
		this.filename = filename;

		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(filename))));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.setFramePosition(0);
		clip.start();

	}
}
