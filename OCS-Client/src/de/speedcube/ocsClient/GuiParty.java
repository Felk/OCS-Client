package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.PartyStates;
import de.speedcube.ocsUtilities.packets.PacketPartyLeave;

public class GuiParty extends GuiPanel {

	private static final long serialVersionUID = 1L;

	private Client client;

	public GuiPanelPartyContainer guiPartyContainer;
	public JScrollPane partiesContainer;
	public JButton leaveButton;

	private Party currentParty;

	public GuiParty(Client client, GuiPanelPartyContainer guiPartyContainer) {
		this.client = client;
		this.guiPartyContainer = guiPartyContainer;

		setLayout(null);
		setBounds(0, 0, 300, 250);

		partiesContainer = new JScrollPane();
		partiesContainer.setBounds(0, 0, getWidth(), getHeight() - 30);

		leaveButton = new JButton(SystemStrings.getString("system.label.party.leave"));
		leaveButton.setBounds(0, 230, 100, 20);
		leaveButton.setName("DefButton");
		leaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				leaveParty();
			}
		});

		partiesContainer.setLayout(null);

		add(partiesContainer);
		add(leaveButton);
		validate();
	}

	private void leaveParty() {
		if (currentParty != null) {
			PacketPartyLeave leavePartyPacket = new PacketPartyLeave();
			leavePartyPacket.partyID = currentParty.id;
			client.sendPacket(leavePartyPacket);
		}
		openGeneralPartyGui();
	}

	public void setActiveParty(Party currentParty) {
		this.currentParty = currentParty;
	}

	public void updatePartyDisplay() {
		if (currentParty == null) return;
		partiesContainer.removeAll();
		JLabel partyDescription = new JLabel("Party: " + currentParty.name + " - Status: " + PartyStates.getString(currentParty.state));
		partyDescription.setBounds(0, 0, 300, 20);
		partiesContainer.add(partyDescription);
		for (int i = 0; i < currentParty.results.length; i++) {
			PartyResultSetGui resultSetGui = new PartyResultSetGui(guiPartyContainer.window.userList, currentParty.results[i]);
			resultSetGui.userLabel.setBounds(0, i * 20 + 20, 300, 20);
			partiesContainer.add(resultSetGui.userLabel);
		}
		validate();
		repaint();
	}

	public void openGeneralPartyGui() {
		guiPartyContainer.remove(this);
		guiPartyContainer.add(guiPartyContainer.generalPartyGui);

		guiPartyContainer.validate();
		guiPartyContainer.repaint();
	}
}
