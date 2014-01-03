package de.speedcube.ocsClient.gui;

import javax.swing.*;

import de.speedcube.ocsClient.*;
import de.speedcube.ocsUtilities.PartyStates;

public class GuiParty extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public GuiPanelPartyContainer partyGuiContainer;
	public JScrollPane partiesContainer;
	public JButton leaveButton;

	public GuiParty(GuiPanelPartyContainer guiPartyContainer) {
		this.partyGuiContainer = guiPartyContainer;

		setLayout(null);
		setBounds(0, 0, 300, 250);

		partiesContainer = new JScrollPane();
		partiesContainer.setBounds(0, 0, getWidth(), getHeight() - 30);

		leaveButton = new JButton(SystemStrings.getString("system.label.party.leave"));
		leaveButton.setBounds(0, 230, 100, 20);
		leaveButton.setName("DefButton");

		partiesContainer.setLayout(null);

		add(partiesContainer);
		add(leaveButton);
		validate();
	}

	public void updatePartyDisplay(Party currentParty) {
		if (currentParty == null) return;
		partiesContainer.removeAll();
		JLabel partyDescription = new JLabel("Party: " + currentParty.name + " - Status: " + PartyStates.getString(currentParty.state));
		partyDescription.setBounds(0, 0, 300, 20);
		partiesContainer.add(partyDescription);
		for (int i = 0; i < currentParty.results.length; i++) {
			PartyResultSetGui resultSetGui = new PartyResultSetGui(OCSClient.userList, currentParty.results[i]);
			resultSetGui.userLabel.setBounds(0, i * 20 + 20, 300, 20);
			partiesContainer.add(resultSetGui.userLabel);
		}
		validate();
		repaint();
	}
}
