package de.speedcube.ocsClient;

import java.util.ArrayList;

import javax.swing.*;

import de.speedcube.ocsClient.chat.ChatHistory;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.Packet;
import de.speedcube.ocsUtilities.packets.PacketChannelEnter;
import de.speedcube.ocsUtilities.packets.PacketChat;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;
import de.speedcube.ocsUtilities.packets.PacketSystemMessage;

public class GuiPanelChat extends GuiPanel {

	private static final long serialVersionUID = 1L;

	private Client client;
	public JTabbedPane chatTabs;
	public ArrayList<ChatHistory> chatAreas;

	public JTextField chatField;
	public JButton chatButton;
	public JCheckBox soundCheckbox;
	public OCSClient window;
	private AudioFile newMsgSound;

	public ArrayList<String> chatMessages;

	public GuiPanelChat(Client client, OCSClient window) {
		this.client = client;
		this.window = window;
		newMsgSound = new AudioFile("/newMsgSound3.wav");
		chatMessages = new ArrayList<String>();

		setLayout(null);

		setBounds(0, 0, 400, 500);
		chatAreas = new ArrayList<ChatHistory>();

		//link listener to open links

		chatField = new JTextField();
		chatField.setBounds(0, 400, 300, 30);
		chatField.addKeyListener(new ChatKeyListener(this));
		chatField.setName("ChatTextfield");

		chatButton = new JButton();
		chatButton.setBounds(300, 400, 100, 30);
		chatButton.setText(SystemStrings.getString("system.label.chat"));
		chatButton.setName("ChatButton");

		ChatButtonListener chatButtonListener = new ChatButtonListener(this);
		chatButton.addActionListener(chatButtonListener);

		soundCheckbox = new JCheckBox(SystemStrings.getString("system.label.activate_sound"));
		soundCheckbox.setBounds(0, 430, 300, 20);
		soundCheckbox.setSelected(true);

		chatTabs = new JTabbedPane();
		chatTabs.setBounds(0, 0, 400, 400);
		chatTabs.setName("ChatChannelTabs");

		add(chatTabs);
		add(chatField);
		add(chatButton);
		add(soundCheckbox);

		genTextAreaStyle(window.userList);
		//chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void processPackets() {
		ArrayList<Packet> packets = client.getData(Packet.CHAT_CHANNEL);

		for (Packet p : packets) {
			if (p instanceof PacketChatBroadcast) {
				ChatHistory ch = getChatChannel(((PacketChatBroadcast) p).chatChannel);
				if (ch != null) {
					ch.addChatMessage((PacketChatBroadcast) p, window.userInfo.userID, soundCheckbox.isSelected(), newMsgSound);
				}
			} else if (p instanceof PacketSystemMessage) {
				if (((PacketSystemMessage) p).global) {
					for (ChatHistory ch : chatAreas) {
						ch.addSystemMessage((PacketSystemMessage) p, soundCheckbox.isSelected(), newMsgSound);
					}
				} else {
					ChatHistory ch = getChatChannel(((PacketSystemMessage) p).chatChannel);
					if (ch != null) {
						ch.addSystemMessage((PacketSystemMessage) p, soundCheckbox.isSelected(), newMsgSound);
					}
				}
			} else if (p instanceof PacketChannelEnter) {
				for (ChatHistory ch : chatAreas) {
					if (ch.name.equals(((PacketChannelEnter) p).chatChannel)) return;
				}
				addChatChannel((PacketChannelEnter) p);
			}
		}
	}

	public void addChatChannel(PacketChannelEnter channelEnterPacket) {
		chatAreas.add(new ChatHistory(channelEnterPacket.chatChannel, window.userList));
		updateChatAreasTabs();
	}

	private ChatHistory getChatChannel(String name) {
		for (ChatHistory ch : chatAreas) {
			if (ch.name.equals(name)) return ch;
		}
		return null;
	}

	public void updateChatAreasTabs() {
		while (chatTabs.getTabCount() > 0) {
			chatTabs.remove(0);
		}
		for (ChatHistory ch : chatAreas) {
			chatTabs.addTab(ch.name, ch.chatScrollPane);
		}
	}

	public void updateChatAreasContent() {
		for (ChatHistory ch : chatAreas) {
			ch.setTextField();
		}
	}

	public void sendChatMessage() {
		if (!chatField.getText().equals("")) {
			PacketChat chatPacket = new PacketChat();
			chatPacket.text = chatField.getText();
			chatPacket.chatChannel = chatAreas.get(chatTabs.getSelectedIndex()).name;

			chatField.setText("");
			client.sendPacket(chatPacket);
		}
	}
}
