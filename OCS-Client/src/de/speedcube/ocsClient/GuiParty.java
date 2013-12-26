package de.speedcube.ocsClient;

import javax.swing.*;

import de.speedcube.ocsClient.network.Client;

public class GuiParty extends GuiPanel {

	private static final long serialVersionUID = 1L;

	private Client client;

	public GuiPanelPartyContainer guiPartyContainer;
	public JScrollPane partiesContainer;
	public JButton leaveButton;

	public GuiParty(Client client, GuiPanelPartyContainer guiPartyContainer) {
		this.client = client;
		this.guiPartyContainer = guiPartyContainer;

		setLayout(null);
		setBounds(0, 0, 300, 250);

		partiesContainer = new JScrollPane();
		partiesContainer.setBounds(0, 30, getWidth(), getHeight());

		leaveButton = new JButton(SystemStrings.getString("system.label.party.leave"));
		leaveButton.setBounds(0, 230, 50, 20);
		leaveButton.setName("DefButton");

		add(partiesContainer);
		add(leaveButton);
		validate();
	}

	public void updatePartyDisplay(Party currentParty) {
		partiesContainer.removeAll();

		if (currentParty == null) return;
		for (int i = 0; i < currentParty.results.length; i++) {
			PartyResultSetGui resultSetGui = new PartyResultSetGui(guiPartyContainer.window.userList, currentParty.results[i]);
			resultSetGui.userLabel.setBounds(0, i * 20, 100, 20);
			partiesContainer.add(resultSetGui.userLabel);
		}
		validate();
		repaint();
	}
}
