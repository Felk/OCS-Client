package de.speedcube.ocsClient.gui;

import de.speedcube.ocsClient.*;

public class GuiTabChat extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public OCSWindow window;
	public GuiPanelChat chatPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelPartyContainer partyContainer;

	public GuiTabChat(OCSWindow window, GuiPanelChat chatPanel, GuiPanelUserlist userlistPanel, GuiPanelPartyContainer partyContainer) {
		this.window = window;
		this.chatPanel = chatPanel;
		this.userlistPanel = userlistPanel;
		this.partyContainer = partyContainer;

		setLayout(null);

		//setBackground(Color.decode("#444444"));
		add(chatPanel);
		add(userlistPanel);
		add(partyContainer);

		setBounds(0, 0, window.getWidth(), window.getHeight());
	}

	public void setLinkListener(OCSLinkListener linkListener) {
		userlistPanel.userlist.addHyperlinkListener(linkListener);
	}
}
