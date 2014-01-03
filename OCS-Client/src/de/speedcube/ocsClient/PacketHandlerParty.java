package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import de.speedcube.ocsClient.gui.GuiPanelPartyContainer;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class PacketHandlerParty extends PacketHandler {

	private GuiPanelPartyContainer partyGuiContainer;

	public HashMap<Integer, Party> parties;
	public int[] partyIDs;
	public Party currentParty;

	public PacketHandlerParty(Client client, GuiPanelPartyContainer partyGuiContainer) {
		super(client);
		this.partyGuiContainer = partyGuiContainer;
		parties = new HashMap<Integer, Party>();
		initListeners();
	}

	@Override
	public void processPacket(Packet p) {
		if (p instanceof PacketPartyList) {
			partyIDs = ((PacketPartyList) p).partyIDs;
		} else if (p instanceof PacketPartyData) {
			PacketPartyData ppd = (PacketPartyData) p;
			Party tempParty = parties.get(ppd.partyID);
			if (tempParty == null) {
				parties.put(ppd.partyID, new Party(ppd.partyID, ppd.ownerID, ppd.type, ppd.rounds, ppd.rounds_counting, ppd.name, ppd.results, ppd.state, OCSClient.userList, client));
			} else {
				tempParty.ownerID = ppd.ownerID;
				tempParty.type = ppd.type;
				tempParty.rounds_num = ppd.rounds;
				tempParty.rounds_counting = ppd.rounds_counting;
				tempParty.name = ppd.name;
				tempParty.results = ppd.results;
				tempParty.state = ppd.state;
			}
			partyGuiContainer.generalPartyGui.updatePartyDisplay(partyIDs, parties);
			partyGuiContainer.partyGui.updatePartyDisplay(currentParty);
		} else if (p instanceof PacketPartyJoin) {
			currentParty = parties.get(((PacketPartyJoin) p).partyID);
			partyGuiContainer.openPartyGui();
		}
	}

	public boolean createParty() {
		PacketPartyCreate ppc = new PacketPartyCreate();
		ppc.name = partyGuiContainer.createPartyGui.nameField.getText();
		try {
			ppc.rounds = Integer.parseInt(partyGuiContainer.createPartyGui.roundNumField.getText());
			ppc.rounds_counting = Integer.parseInt(partyGuiContainer.createPartyGui.roundNumCountingField.getText());
		} catch (NumberFormatException e) {
			partyGuiContainer.createPartyGui.roundNumCountingField.setText("");
			partyGuiContainer.createPartyGui.roundNumField.setText("");
			return false;
		}
		ppc.type = partyGuiContainer.createPartyGui.typeBox.getSelectedIndex() == 0 ? (byte) 1 : (byte) 2;
		ppc.scrambleType = "3x3";
		client.sendPacket(ppc);

		return true;
	}

	private void leaveParty() {
		if (currentParty != null) {
			PacketPartyLeave leavePartyPacket = new PacketPartyLeave();
			leavePartyPacket.partyID = currentParty.id;
			client.sendPacket(leavePartyPacket);
		}
		partyGuiContainer.openGeneralPartyGui();
	}

	private void initListeners() {
		partyGuiContainer.partyGui.leaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				leaveParty();
			}
		});

		partyGuiContainer.createPartyGui.createPartyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (createParty()) {
					partyGuiContainer.openGeneralPartyGui();
				}
			}
		});

		partyGuiContainer.createPartyGui.backPartyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				partyGuiContainer.openGeneralPartyGui();
			}
		});

		partyGuiContainer.generalPartyGui.createPartyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				partyGuiContainer.openCreatePartyGui();
			}
		});
	}
}
