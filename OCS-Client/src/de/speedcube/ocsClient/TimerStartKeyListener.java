package de.speedcube.ocsClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.speedcube.ocsClient.network.Client;

public class TimerStartKeyListener implements KeyListener {

	private Client client;
	private GuiPanelTimer timerGui;
	private TimerUpdateThread timerUpdateThread;
	private boolean startNext;

	public TimerStartKeyListener(Client client, GuiPanelTimer timerGui) {
		this.client = client;
		this.timerGui = timerGui;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (timerUpdateThread != null) {
				timerUpdateThread.stopTimer();
				timerUpdateThread = null;
				startNext = false;
			} else {
				startNext = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (timerUpdateThread == null && startNext) {
				timerUpdateThread = new TimerUpdateThread(timerGui, System.nanoTime());
				timerUpdateThread.start();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
