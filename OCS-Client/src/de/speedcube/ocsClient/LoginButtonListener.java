package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketRegistration;
import de.speedcube.ocsUtilities.packets.PacketSaltGet;

public class LoginButtonListener implements ActionListener {

	private Client client;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private boolean register;

	public LoginButtonListener(Client client, JTextField usernameField, JPasswordField passwordField, boolean register) {
		this.client = client;
		this.usernameField = usernameField;
		this.passwordField = passwordField;
		this.register = register;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!register) {
			PacketSaltGet userNamePacket = new PacketSaltGet();
			userNamePacket.username = usernameField.getText();
			client.sendPacket(userNamePacket);
		} else {
			PacketRegistration registerPacket = new PacketRegistration();
			registerPacket.username = usernameField.getText();
			registerPacket.password = new String(passwordField.getPassword());
			client.sendPacket(registerPacket);
		}
	}
}
