package de.speedcube.ocsClient.chat;

import java.util.ArrayList;

import de.speedcube.ocsClient.*;
import de.speedcube.ocsClient.gui.GuiChatChannel;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;
import de.speedcube.ocsUtilities.packets.PacketSystemMessage;

public class ChatHistory {

	public String name;
	public GuiChatChannel channelGui;
	public ArrayList<Message> messages;

	public ChatHistory(String name, Client client) {
		this.name = name;
		channelGui = new GuiChatChannel(new OCSLinkListener(client));
		messages = new ArrayList<Message>();
	}

	public void addChatMessage(PacketChatBroadcast message, int userID, boolean playSound, AudioFile newMsgSound) {
		messages.add(new ChatMessage(message.timestamp, message.text, message.userId));
		channelGui.setTextField(messages);
		if (message.userId != userID && playSound) {
			newMsgSound.play();
		}
	}

	public void addSystemMessage(PacketSystemMessage message, boolean playSound, AudioFile newMsgSound) {
		messages.add(new SystemMessage(message.timestamp, SystemStrings.getString(message.msg, message.values)));
		channelGui.setTextField(messages);
		if (playSound) newMsgSound.play();
	}
}
