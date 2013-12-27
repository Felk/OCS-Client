package de.speedcube.ocsClient;

import java.util.ArrayList;
import java.util.HashMap;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class GuiPanelPartyContainer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public GuiPartyGeneral generalPartyGui;
	public GuiPartyCreate createPartyGui;
	public GuiParty partyGui;

	public Client client;
	public OCSClient window;
	public HashMap<Integer, Party> parties;
	public int[] partyIDs;
	public Party currentParty;

	public GuiPanelPartyContainer(Client client, OCSClient window) {
		this.client = client;
		this.window = window;
		parties = new HashMap<Integer, Party>();

		setLayout(null);
		setBounds(500, 250, 300, 250);

		generalPartyGui = new GuiPartyGeneral(client, this);
		createPartyGui = new GuiPartyCreate(client, this);
		partyGui = new GuiParty(client, this);

		add(generalPartyGui);

		validate();
	}

	public void processPackets() {
		ArrayList<Packet> packets = client.getData(Packet.PARTY_CHANNEL);

		for (Packet p : packets) {
			if (p instanceof PacketPartyList) {
				partyIDs = ((PacketPartyList) p).partyIDs;
			} else if (p instanceof PacketPartyData) {
				PacketPartyData ppd = (PacketPartyData) p;
				Party tempParty = parties.get(ppd.partyID);
				if (tempParty == null) {
					parties.put(ppd.partyID, new Party(ppd.partyID, ppd.ownerID, ppd.type, ppd.rounds, ppd.rounds_counting, ppd.name, ppd.results, ppd.state, window.userList, client));
				} else {
					tempParty.ownerID = ppd.ownerID;
					tempParty.type = ppd.type;
					tempParty.rounds_num = ppd.rounds;
					tempParty.rounds_counting = ppd.rounds_counting;
					tempParty.name = ppd.name;
					tempParty.results = ppd.results;
				}
				generalPartyGui.updatePartyDisplay(partyIDs, parties);
				partyGui.setActiveParty(currentParty);
				partyGui.updatePartyDisplay();
			} else if (p instanceof PacketPartyJoin) {
				currentParty = parties.get(((PacketPartyJoin) p).partyID);
				openPartyGui();
			}
		}
	}

	public void openPartyGui() {
		removeAll();
		add(partyGui);

		validate();
		repaint();

	}
}
