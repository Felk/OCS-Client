package de.speedcube.ocsClient;

import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
