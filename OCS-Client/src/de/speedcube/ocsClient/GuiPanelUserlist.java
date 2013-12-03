package de.speedcube.ocsClient;

import java.util.HashMap;

import javax.swing.JTextArea;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	public JTextArea userlist;
	public HashMap<Integer, String> usermap;

	public GuiPanelUserlist(Client client, OCSClient window) {
		usermap = new HashMap<Integer, String>();
		setLayout(null);
		setBounds(500, 0, 300, window.getHeight());

		userlist = new JTextArea();
		userlist.setBounds(500, 0, 300, 400);
		userlist.setEditable(false);

		add(userlist);

		validate();
	}

	public void updateUserlist(PacketUserlist userlistPacket) {
		userlist.setText("");
		for (String username : userlistPacket.usernames) {
			userlist.append(username + "\n");
		}

		usermap.clear();
		for (int i = 0; i < userlistPacket.usernames.length; i++) {
			usermap.put(userlistPacket.userIds[i], userlistPacket.usernames[i]);
		}
	}

	public String getUserNameByID(int userID) {
		return usermap.get(userID);
	}
}
