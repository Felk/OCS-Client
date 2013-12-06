package de.speedcube.ocsClient;

import javax.swing.JTextArea;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	public JTextArea userlist;
	private OCSClient window;

	public GuiPanelUserlist(Client client, OCSClient window) {
		this.window = window;

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
		for (int i = 0; i < userlistPacket.userIds.length; i++) {
			userlist.append(window.userList.getUserNameByID(userlistPacket.userIds[i]) + " : " + userlistPacket.userIds[i] + "\n");
		}
	}
}
