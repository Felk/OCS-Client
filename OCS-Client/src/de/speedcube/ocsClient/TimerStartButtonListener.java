package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.speedcube.ocsClient.network.Client;

public class TimerStartButtonListener implements ActionListener {

	private Client client;
	private GuiPanelTimer timerGui;
	private TimerUpdateThread timerUpdateThread;

	public TimerStartButtonListener(Client client, GuiPanelTimer timerGui) {
		this.client = client;
		this.timerGui = timerGui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (timerUpdateThread == null) {
			timerUpdateThread = new TimerUpdateThread(timerGui, System.nanoTime());
			timerUpdateThread.start();
		} else {
			timerUpdateThread.stopTimer();
			timerUpdateThread = null;
		}
	}

}
