package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatButtonListener implements ActionListener {

	private GuiPanelChat panel;

	public ChatButtonListener(GuiPanelChat panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.sendChatMessage();
	}

}
