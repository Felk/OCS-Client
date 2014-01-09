package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import de.speedcube.ocsClient.gui.GuiPanelPartyContainer;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.PartyTimeTypes;
import de.speedcube.ocsUtilities.ScrambleTypes;
import de.speedcube.ocsUtilities.packets.*;

public class PacketHandlerParty extends PacketHandler {

	private GuiPanelPartyContainer partyGuiContainer;

	public HashMap<Integer, Party> parties;
	public int[] partyIDs;
	public Party currentParty;
	public long currentTime;

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
				parties.put(ppd.partyID, new Party(ppd.partyID, ppd.ownerID, ppd.type, ppd.rounds, ppd.rounds_counting, ppd.round, ppd.name, ppd.results, ppd.state, OCSClient.userList, client));
			} else {
				tempParty.ownerID = ppd.ownerID;
				tempParty.type = ppd.type;
				tempParty.rounds_num = ppd.rounds;
				tempParty.rounds_counting = ppd.rounds_counting;
				tempParty.round = ppd.round;
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
		if (ppc.name.equals("")) return false;
		try {
			ppc.rounds = Integer.parseInt(partyGuiContainer.createPartyGui.roundNumField.getText());
			//ppc.rounds_counting = Integer.parseInt(partyGuiContainer.createPartyGui.roundNumCountingField.getText());
			ppc.rounds_counting = (ppc.rounds - 2) < 1 ? 1 : ppc.rounds - 2;
		} catch (NumberFormatException e) {
			partyGuiContainer.createPartyGui.roundNumCountingField.setText("");
			partyGuiContainer.createPartyGui.roundNumField.setText("");
			return false;
		}
		ppc.type = partyGuiContainer.createPartyGui.typeBox.getSelectedIndex() == 0 ? (byte) 1 : (byte) 2;
		ppc.scrambleType = ScrambleTypes.types.get(partyGuiContainer.createPartyGui.scrambleBox.getSelectedItem());
		client.sendPacket(ppc);

		return true;
	}

	private void leaveParty() {
		if (currentParty != null) {
			PacketPartyLeave leavePartyPacket = new PacketPartyLeave();
			leavePartyPacket.partyID = currentParty.id;
			client.sendPacket(leavePartyPacket);
			currentParty = null;
		}
		partyGuiContainer.openGeneralPartyGui();
	}

	public void openTimer() {
		partyGuiContainer.openTimerGui();

		partyGuiContainer.timerGui.reset();
	}

	private void initListeners() {
		partyGuiContainer.partyGui.openTimerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyGuiContainer.openTimerGui();
				partyGuiContainer.timerGui.timerLabel.requestFocus();
			}
		});

		partyGuiContainer.partyGui.startPartyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacketPartyStart partyStartPacket = new PacketPartyStart();
				partyStartPacket.partyID = currentParty.id;
				client.sendPacket(partyStartPacket);

			}
		});

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

		partyGuiContainer.timerGui.okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacketPartyTime timePacket = new PacketPartyTime();
				timePacket.time = (int) currentTime / 1000000;
				timePacket.partyID = currentParty.id;
				client.sendPacket(timePacket);
				partyGuiContainer.openPartyGui();
			}
		});

		partyGuiContainer.timerGui.extraTimeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacketPartyTime timePacket = new PacketPartyTime();
				timePacket.time = (int) (currentTime / 1000000) + 2000;
				timePacket.partyID = currentParty.id;
				client.sendPacket(timePacket);
				partyGuiContainer.openPartyGui();
			}
		});

		partyGuiContainer.timerGui.dnfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacketPartyTime timePacket = new PacketPartyTime();
				timePacket.time = PartyTimeTypes.DNF;
				timePacket.partyID = currentParty.id;
				client.sendPacket(timePacket);
				partyGuiContainer.openPartyGui();
			}
		});

		partyGuiContainer.timerGui.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyGuiContainer.openPartyGui();
			}
		});

		TimerStartKeyListener timerStartKeyListener = new TimerStartKeyListener(partyGuiContainer.timerGui, this);
		partyGuiContainer.timerGui.addKeyListener(timerStartKeyListener);

	}

	@Override
	public void reset() {
		parties.clear();
		partyIDs = null;
		currentParty = null;
		partyGuiContainer.openGeneralPartyGui();
	}

	public void setTimerStopped(long time) {
		partyGuiContainer.timerGui.okButton.requestFocus();
		currentTime = time;
	}
}
