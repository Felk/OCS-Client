package de.speedcube.ocsClient;

import java.awt.Color;

public class GuiPanelChatContainer extends GuiPanel {

	public OCSClient window;
	public GuiPanelChat chatPanel;
	public GuiPanelUserlist userlistPanel;

	public GuiPanelChatContainer(OCSClient window, GuiPanelChat chatPanel, GuiPanelUserlist userlistPanel) {
		this.window = window;
		this.chatPanel = chatPanel;
		this.userlistPanel = userlistPanel;

		setLayout(null);

		setBackground(Color.decode("#444444"));
		add(chatPanel);
		add(userlistPanel);

		setBounds(0, 0, window.getWidth(), window.getHeight());
	}
}
