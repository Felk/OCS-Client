package de.speedcube.ocsClient;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import de.speedcube.ocsUtilities.packets.PacketSound;

public class RecorderThread extends Thread {

	private boolean recordingStopped, running;
	private byte[] data;
	public static final int SAMPLE_RATE = 22050;
	public static final int SAMPLE_SIZE = 16;
	public static final byte CHANNELS = 1;
	public Object finalizeWaitObject = new Object();
	public Object startWaitObject = new Object();

	public static final AudioFormat FORMAT = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, true, true);
	private TargetDataLine mic = null;

	/* SO KÖNNTE MAN ES VON AUSSEN VERWENDEN:
	 * 
	   	RecorderThread thread = new RecorderThread();
	   	
		thread.startRecording(); // z.B. wenn Knopf gedrückt
								 // ...
		thread.stopRecording();  // z.B. wenn Knopf losgelassen
		synchronized(thread.finalizeWaitObject) {
			thread.finalizeWaitObject.wait();
		}
		
		
		byte[] out = thread.getData();
		    ODER DIREKT
		PacketSound packet = thread.getSoundPacket();
	 */
	
	public RecorderThread() {

		setName("AudioCaptureThread");
		running = true;
		recordingStopped = false;

		try {
			mic = AudioSystem.getTargetDataLine(FORMAT);
			mic.open();
		} catch (LineUnavailableException e) {
			System.out.println("No valid Microphone-Line accessible");
			e.printStackTrace();
			return;
		}

		this.start();
	}

	@Override
	public void run() {

		while (running) {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int numBytesRead = 0;
			byte[] data = new byte[mic.getBufferSize()];

			mic.start();

			while (!recordingStopped) {
				numBytesRead = mic.read(data, 0, data.length);
				out.write(data, 0, numBytesRead);
			}

			mic.stop();
			this.data = out.toByteArray();

			synchronized (finalizeWaitObject) {
				finalizeWaitObject.notify();
			}

			try {
				synchronized (startWaitObject) {
					startWaitObject.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void stopWaiting() {
		synchronized (startWaitObject) {
			startWaitObject.notify();
		}
	}

	public void startRecording() {
		recordingStopped = false;
		stopWaiting();
	}

	public void stopRecording() {
		this.recordingStopped = true;
	}

	public void stopRecorderThread() {
		running = false;
		stopWaiting();
	}

	public byte[] getData() {
		return data;
	}

	public PacketSound getPacketSound() {
		PacketSound packet = new PacketSound();
		packet.sampleRate = SAMPLE_RATE;
		packet.sampleSize = SAMPLE_SIZE;
		packet.channels = CHANNELS;
		packet.soundData = data;
		return packet;
	}

}
