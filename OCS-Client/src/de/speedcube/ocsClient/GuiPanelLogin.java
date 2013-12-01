package de.speedcube.ocsClient;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.speedcube.ocsClient.network.Client;

public class GuiPanelLogin extends GuiPanel {

	public JButton loginButton;
	public JTextField usernameField;
	public JPasswordField passwordField;

	public GuiPanelLogin(Client client, OCSClient window) {
		setLayout(null);
		setBounds(0, 0, window.getWidth(), window.getHeight());

		usernameField = new JTextField();
		usernameField.setSize(150, 30);
		usernameField.setText("Testuser");

		passwordField = new JPasswordField();
		passwordField.setBounds(0, 40, 150, 30);
		passwordField.setText("42");

		loginButton = new JButton();
		loginButton.setBounds(0, 80, 150, 30);
		loginButton.setText("login");
		LoginButtonListener loginButtonListener = new LoginButtonListener(client, usernameField);
		loginButton.addActionListener(loginButtonListener);

		add(loginButton);
		add(usernameField);
		add(passwordField);

		validate();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}
}
