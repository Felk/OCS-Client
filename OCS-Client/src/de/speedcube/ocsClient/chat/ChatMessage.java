package de.speedcube.ocsClient.chat;

import de.speedcube.ocsClient.GuiPanel;
import de.speedcube.ocsClient.UserList;

public class ChatMessage extends Message {
	public int userID;

	public ChatMessage(long timestamp, String message, int userID) {
		super(timestamp, message);
		this.userID = userID;
	}

	@Override
	public String toString(UserList userlist) {
		return ("<span class ='time'>" + timestampToString(timestamp) + "</span>  <span class ='u" + userID + "'>" + userlist.getUserNameByID(userID) + "</span> - " + GuiPanel.setLinks(GuiPanel.escapeHTML(message)));
	}
}
