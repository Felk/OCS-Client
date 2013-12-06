package de.speedcube.ocsClient;

public class TimerUpdateThread extends Thread {

	private GuiPanelTimer timerGui;
	private long startTimeNanos;
	private long startTimerMillis;
	private boolean stop = false;

	public TimerUpdateThread(GuiPanelTimer timerGui, long startTime) {
		this.timerGui = timerGui;
		this.startTimeNanos = startTime;
		this.startTimerMillis = System.currentTimeMillis();
	}

	public String stopTimer() {
		stop = true;
		return convertToString(System.nanoTime() - startTimeNanos);
	}

	@Override
	public void run() {
		while (!stop) {
			timerGui.setText(convertToString((System.currentTimeMillis() - startTimerMillis) * 1000000));
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String convertToString(long timeDelta) {
		int timeInMillis = (int) (timeDelta / 1000000);
		int seconds = timeInMillis / 1000;
		int millis = timeInMillis % 1000;

		int minutes = seconds / 60;
		int temp1 = millis / 10;
		seconds %= 60;

		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds) + "," + (temp1 < 10 ? "0" + temp1 : temp1);
	}
}
