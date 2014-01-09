package de.speedcube.ocsClient.gui;

import java.awt.Dimension;

import javax.swing.*;

import de.speedcube.ocsClient.*;
import de.speedcube.ocsUtilities.PartyStates;

public class GuiParty extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public GuiPanelPartyContainer partyGuiContainer;
	public JPanel partiesContainer;
	public JScrollPane partiesContainerScrollPane;
	public JButton leaveButton;
	public JButton startPartyButton;
	public JButton openTimerButton;

	public GuiParty(GuiPanelPartyContainer guiPartyContainer) {
		this.partyGuiContainer = guiPartyContainer;

		setLayout(null);
		setBounds(0, 0, 300, 250);

		partiesContainerScrollPane = new JScrollPane();
		partiesContainerScrollPane.setBounds(0, 0, getWidth(), 200);
		partiesContainerScrollPane.setLayout(null);
		partiesContainer = new JPanel();
		partiesContainer.setBounds(0, 0, 30000, 20000);
		partiesContainer.setLayout(new BoxLayout(partiesContainer, BoxLayout.Y_AXIS));
		partiesContainerScrollPane.add(partiesContainer);

		leaveButton = new JButton(SystemStrings.getString("system.label.party.leave"));
		leaveButton.setBounds(0, 230, 100, 20);
		leaveButton.setName("DefButton");

		startPartyButton = new JButton(SystemStrings.getString("system.label.party.start"));
		startPartyButton.setBounds(110, 200, 100, 20);
		startPartyButton.setName("DefButton");

		openTimerButton = new JButton(SystemStrings.getString("system.label.party.openTimer"));
		openTimerButton.setBounds(0, 200, 100, 20);
		openTimerButton.setName("DefButton");

		add(partiesContainerScrollPane);
		add(leaveButton);
		add(startPartyButton);
		add(openTimerButton);
		validate();
	}

	public void updatePartyDisplay(Party currentParty) {
		if (currentParty == null) return;
		partiesContainer.removeAll();
		JLabel partyDescription = new JLabel("Party: " + currentParty.name + " - Status: " + PartyStates.getString(currentParty.state) + " - Runde: " + currentParty.round);
		//partyDescription.setBounds(0, 0, 300, 20);
		partyDescription.setSize(new Dimension(300, 20));
		partyDescription.setPreferredSize(new Dimension(300, 20));
		partyDescription.setMaximumSize(new Dimension(300, 20));
		partyDescription.setMinimumSize(new Dimension(300, 20));

		partiesContainer.add(partyDescription);
		for (int i = 0; i < currentParty.results.length; i++) {
			PartyResultSetGui resultSetGui = new PartyResultSetGui(OCSClient.userList, currentParty.results[i]);
			partiesContainer.add(resultSetGui.userLabel);

			/*if (currentParty.results[i].getUserID() == OCSClient.userInfo.userID) {
				System.out.println("1");
				if (currentParty.state == PartyStates.RUNNING) {
					System.out.println("2");
					int tempTime = currentParty.results[i].getTimes()[currentParty.round - 1];
					if (tempTime == PartyTimeTypes.DNS) {
						System.out.println("3");
						openTimerButton.setVisible(true);
					} else {
						openTimerButton.setVisible(false);
					}
				} else {
					openTimerButton.setVisible(false);
				}
			}*/
		}
		validate();
		repaint();

	}
}
