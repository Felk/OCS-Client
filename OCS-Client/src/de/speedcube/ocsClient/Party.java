package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.PartyResultSet;
import de.speedcube.ocsUtilities.PartyStates;
import de.speedcube.ocsUtilities.packets.PacketPartyJoin;

public class Party {
	public JLabel nameLabel;
	public JButton joinButton;

	public int state = PartyStates.OPEN;
	public int ownerID;
	public byte type;

	public int id;
	public int rounds_num;
	public int rounds_counting;
	public String name;
	public PartyResultSet[] results;

	private Client client;

	public Party(int id, int ownerID, byte type, int rounds, int counting, String name, PartyResultSet[] results, int state, UserList userlist, Client client) {
		this.id = id;
		this.ownerID = ownerID;
		this.type = type;
		this.rounds_num = rounds;
		this.rounds_counting = counting;
		this.name = name;
		this.results = results;
		this.state = state;
		this.client = client;

		nameLabel = new JLabel();
		nameLabel.setText(name);

		joinButton = new JButton();
		joinButton.setName("PartyJoinButton");
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendJoinParty();
			}
		});
	}

	private void sendJoinParty() {
		PacketPartyJoin ppj = new PacketPartyJoin();
		ppj.partyID = id;
		client.sendPacket(ppj);
	}

	public void setOver() {
		state = PartyStates.OVER;
	}

	public boolean isOver() {
		return state == PartyStates.OVER;
	}

	public boolean isRunning() {
		return state == PartyStates.RUNNING;
	}

	public boolean isOpen() {
		return state == PartyStates.OPEN;
	}
}
