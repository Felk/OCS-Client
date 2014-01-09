package de.speedcube.ocsClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.speedcube.ocsClient.gui.GuiPartyTimer;

public class TimerStartKeyListener implements KeyListener {

	private GuiPartyTimer timerGui;
	private PacketHandlerParty partyPacketHandler;
	private TimerUpdateThread timerUpdateThread;
	private boolean startNext;

	public TimerStartKeyListener(GuiPartyTimer timerGui, PacketHandlerParty partyPacketHandler) {
		this.timerGui = timerGui;
		this.partyPacketHandler = partyPacketHandler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (timerUpdateThread != null) {
				long endTime = timerUpdateThread.stopTimer();
				partyPacketHandler.setTimerStopped(endTime);
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
