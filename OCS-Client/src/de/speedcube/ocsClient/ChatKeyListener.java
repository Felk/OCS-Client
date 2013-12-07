package de.speedcube.ocsClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatKeyListener implements KeyListener {

	private GuiPanelChat panel;

	public ChatKeyListener(GuiPanelChat panel) {
		this.panel = panel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			panel.sendChatMessage();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
