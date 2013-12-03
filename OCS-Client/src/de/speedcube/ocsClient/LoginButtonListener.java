package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketLogin;
import de.speedcube.ocsUtilities.packets.PacketSaltGet;

public class LoginButtonListener implements ActionListener {

	private Client client;
	private JTextField usernameField;

	public LoginButtonListener(Client client, JTextField usernameField) {
		this.client = client;
		this.usernameField = usernameField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PacketSaltGet userNamePacket = new PacketSaltGet();
		userNamePacket.username = usernameField.getText();
		client.sendPacket(userNamePacket);
	}

}
