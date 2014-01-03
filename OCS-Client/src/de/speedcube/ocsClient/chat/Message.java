package de.speedcube.ocsClient.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message {
	public long timestamp;
	public String message;

	public Message(long timestamp, String message) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String timestampToString(long timestamp) {
		SimpleDateFormat chatTime = new SimpleDateFormat("H:mm");
		return chatTime.format(new Date(timestamp));
	}

	public abstract String toString();
}
