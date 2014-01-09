package de.speedcube.ocsClient;

import de.speedcube.ocsClient.gui.GuiPanel;
import de.speedcube.ocsClient.gui.GuiPartyTimer;

public class TimerUpdateThread extends Thread {

	private GuiPartyTimer timerGui;
	private long startTimeNanos;
	private long startTimerMillis;
	private boolean stop = false;

	public TimerUpdateThread(GuiPartyTimer timerGui, long startTime) {
		this.timerGui = timerGui;
		this.startTimeNanos = startTime;
		this.startTimerMillis = System.currentTimeMillis();
	}

	public long stopTimer() {
		stop = true;
		timerGui.setText(GuiPanel.convertTimeToString(System.nanoTime() - startTimeNanos));

		timerGui.enableButtons();
		return System.nanoTime() - startTimeNanos;
	}

	@Override
	public void run() {
		while (!stop) {
			timerGui.setText(GuiPanel.convertTimeToString((System.currentTimeMillis() - startTimerMillis) * 1000000));
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
