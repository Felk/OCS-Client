package de.speedcube.ocsClient.chat;

import de.speedcube.ocsClient.gui.GuiPanel;

public class SystemMessage extends Message {

	public SystemMessage(long timestamp, String message) {
		super(timestamp, message);
	}

	@Override
	public String toString() {
		return ("<span class ='time'>" + timestampToString(timestamp) + "</span>  <span class ='system'>" + GuiPanel.setLinks(GuiPanel.escapeHTML(message)) + "</span>");
	}
}
