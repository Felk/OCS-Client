package de.speedcube.ocsClient.chat;

import de.speedcube.ocsClient.OCSClient;
import de.speedcube.ocsClient.gui.GuiPanel;

public class ChatMessage extends Message {
	public int userID;

	public ChatMessage(long timestamp, String message, int userID) {
		super(timestamp, message);
		this.userID = userID;
	}

	@Override
	public String toString() {
		return ("<span class ='time'>" + timestampToString(timestamp) + "</span>  <span class ='u" + userID + "'>" + OCSClient.userList.getUserNameByID(userID) + "</span> - " + GuiPanel.setLinks(GuiPanel.escapeHTML(message)));
	}
}
