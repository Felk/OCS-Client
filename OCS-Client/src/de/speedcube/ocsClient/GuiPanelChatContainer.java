package de.speedcube.ocsClient;

public class GuiPanelChatContainer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public OCSClient window;
	public GuiPanelChat chatPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelPartyContainer partyContainer;

	public GuiPanelChatContainer(OCSClient window, GuiPanelChat chatPanel, GuiPanelUserlist userlistPanel, GuiPanelPartyContainer partyContainer) {
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
}
