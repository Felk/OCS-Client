package de.speedcube.ocsClient.gui;

import java.util.HashMap;

import javax.swing.*;

import de.speedcube.ocsClient.Party;
import de.speedcube.ocsClient.SystemStrings;

public class GuiPartyGeneral extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public GuiPanelPartyContainer guiPartyContainer;
	public JScrollPane partiesContainer;
	public JButton createPartyButton;

	public GuiPartyGeneral(GuiPanelPartyContainer guiPartyContainer) {
		this.guiPartyContainer = guiPartyContainer;

		setLayout(null);
		setBounds(0, 0, 300, 250);

		partiesContainer = new JScrollPane();
		partiesContainer.setBounds(0, 30, getWidth(), getHeight());

		createPartyButton = new JButton();
		createPartyButton.setName("DefButton");
		createPartyButton.setText(SystemStrings.getString("system.label.party.create"));
		createPartyButton.setBounds(0, 0, 100, 30);

		add(createPartyButton);

		JLabel testlabel = new JLabel("test");
		testlabel.setBounds(0, 0, 100, 20);
		partiesContainer.add(testlabel);
		add(partiesContainer);

		validate();
	}

	public void updatePartyDisplay(int[] partyIDs, HashMap<Integer, Party> parties) {
		partiesContainer.removeAll();
		for (int i = 0; i < partyIDs.length; i++) {
			Party tempParty = parties.get(partyIDs[i]);
			if (tempParty != null) {

				tempParty.nameLabel.setBounds(25, i * 20, 150, 20);
				partiesContainer.add(tempParty.nameLabel);

				tempParty.joinButton.setBounds(0, i * 20, 20, 20);
				//if (tempParty.isOpen()) {
				partiesContainer.add(tempParty.joinButton);
				//}
			}
		}
		validate();
		repaint();
	}
}
