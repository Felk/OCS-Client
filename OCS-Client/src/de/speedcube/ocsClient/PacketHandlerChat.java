package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import de.speedcube.ocsClient.chat.ChatHistory;
import de.speedcube.ocsClient.gui.GuiPanelChat;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class PacketHandlerChat extends PacketHandler {

	public ArrayList<ChatHistory> chatRooms;

	public GuiPanelChat chatPanel;
	private AudioFile newMsgSound;
	public ActionListener chatListener;

	public PacketHandlerChat(Client client, GuiPanelChat chatPanel) {
		super(client);
		this.chatPanel = chatPanel;
		chatRooms = new ArrayList<ChatHistory>();
		newMsgSound = new AudioFile("/newMsgSound3.wav");
		chatListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendChatMessage();
			}
		};

		chatPanel.chatButton.addActionListener(chatListener);
		chatPanel.chatField.addActionListener(chatListener);

		initListeners();
	}

	@Override
	public void processPacket(Packet p) {

		if (p instanceof PacketChatBroadcast) {
			ChatHistory ch = getChatChannel(((PacketChatBroadcast) p).chatChannel);
			if (ch != null) {
				ch.addChatMessage((PacketChatBroadcast) p, OCSClient.userInfo.userID, chatPanel.soundCheckbox.isSelected(), newMsgSound);
			}
		} else if (p instanceof PacketSystemMessage) {
			if (((PacketSystemMessage) p).global) {
				for (ChatHistory ch : chatRooms) {
					ch.addSystemMessage((PacketSystemMessage) p, chatPanel.soundCheckbox.isSelected(), newMsgSound);
				}
			} else {
				ChatHistory ch = getChatChannel(((PacketSystemMessage) p).chatChannel);
				if (ch != null) {
					ch.addSystemMessage((PacketSystemMessage) p, chatPanel.soundCheckbox.isSelected(), newMsgSound);
				}
			}
		} else if (p instanceof PacketChannelEnter) {
			for (ChatHistory ch : chatRooms) {
				if (ch.name.equals(((PacketChannelEnter) p).chatChannel)) return;
			}
			addChatChannel((PacketChannelEnter) p);
		} else if (p instanceof PacketChannelLeave) {
			for (int i = 0; i < chatRooms.size(); i++) {
				if (chatRooms.get(i).name.equals(((PacketChannelLeave) p).chatChannel)) {
					chatRooms.remove(i);
					chatPanel.updateChatAreasTabs(chatRooms);
				}
			}
		}
	}

	public void addChatChannel(PacketChannelEnter channelEnterPacket) {
		chatRooms.add(new ChatHistory(channelEnterPacket.chatChannel, client));
		chatPanel.updateChatAreasTabs(chatRooms);
	}

	private ChatHistory getChatChannel(String name) {
		for (ChatHistory ch : chatRooms) {
			if (ch.name.equals(name)) return ch;
		}
		return null;
	}

	public ChatHistory getCurrentChannel() {
		return chatRooms.get(chatPanel.chatTabs.getSelectedIndex());
	}

	public void sendChatMessage() {
		if (!chatPanel.chatField.getText().equals("")) {
			PacketChat chatPacket = new PacketChat();
			chatPacket.text = chatPanel.chatField.getText();
			chatPacket.chatChannel = getCurrentChannel().name;

			chatPanel.chatField.setText("");
			client.sendPacket(chatPacket);
		}
	}

	private void initListeners() {
		chatPanel.leaveChannelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacketChannelLeave leaveChannelPacket = new PacketChannelLeave();
				leaveChannelPacket.chatChannel = getCurrentChannel().name;
				client.sendPacket(leaveChannelPacket);
			}
		});
	}

	@Override
	public void reset() {
		chatRooms.clear();
	}
}
