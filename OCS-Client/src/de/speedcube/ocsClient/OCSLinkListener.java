package de.speedcube.ocsClient;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketChannelWhisper;

public class OCSLinkListener implements HyperlinkListener {

	Client client;

	public OCSLinkListener(Client client) {
		this.client = client;
	}

	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				int userID = getUserID(e.getDescription());
				System.out.println(e.getDescription());
				if (userID != -1) {
					PacketChannelWhisper whisperPacket = new PacketChannelWhisper();
					whisperPacket.userID = userID;
					client.sendPacket(whisperPacket);
				} else {
					Desktop.getDesktop().browse(e.getURL().toURI());
				}
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}

	private int getUserID(String description) {
		if (description.matches("^u:(\\d+)$")) {
			String userIDString = description.replaceAll("^u:(\\d+)$", "$1");
			int userID = Integer.parseInt(userIDString);
			return userID;
		} else {
			return -1;
		}
	}

}
