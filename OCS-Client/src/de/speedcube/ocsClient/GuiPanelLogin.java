package de.speedcube.ocsClient;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.speedcube.ocsClient.network.Client;

public class GuiPanelLogin extends GuiPanel {

	public JLabel alertLabel;

	public JTextField usernameFieldLogin;
	public JPasswordField passwordFieldLogin;
	public JButton loginButton;

	public JTextField usernameFieldRegister;
	public JPasswordField passwordFieldRegister;
	public JButton registerButton;

	public GuiPanelLogin(Client client, OCSClient window) {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		//setBounds(0, 0, window.getWidth(), window.getHeight());

		alertLabel = new JLabel();
		alertLabel.setText("");
		//login

		usernameFieldLogin = new JTextField();
		setComponentSize(usernameFieldLogin);

		passwordFieldLogin = new JPasswordField();
		setComponentSize(passwordFieldLogin);

		loginButton = new JButton();
		setComponentSize(loginButton);
		loginButton.setText("login");
		LoginButtonListener loginButtonListener = new LoginButtonListener(client, usernameFieldLogin, passwordFieldLogin, false);
		loginButton.addActionListener(loginButtonListener);

		//registration

		usernameFieldRegister = new JTextField();
		setComponentSize(usernameFieldRegister);

		passwordFieldRegister = new JPasswordField();
		setComponentSize(passwordFieldRegister);

		registerButton = new JButton();
		setComponentSize(registerButton);
		registerButton.setText("register");
		LoginButtonListener registerButtonListener = new LoginButtonListener(client, usernameFieldRegister, passwordFieldRegister, true);
		registerButton.addActionListener(registerButtonListener);

		//layout

		layout.putConstraint(SpringLayout.NORTH, usernameFieldLogin, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, usernameFieldLogin, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, passwordFieldLogin, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, loginButton, 50, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, passwordFieldLogin, 15, SpringLayout.SOUTH, usernameFieldLogin);
		layout.putConstraint(SpringLayout.NORTH, loginButton, 35, SpringLayout.SOUTH, passwordFieldLogin);

		layout.putConstraint(SpringLayout.EAST, usernameFieldRegister, -50, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, passwordFieldRegister, -50, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, registerButton, -50, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH, usernameFieldRegister, 0, SpringLayout.NORTH, usernameFieldLogin);
		layout.putConstraint(SpringLayout.NORTH, passwordFieldRegister, 0, SpringLayout.NORTH, passwordFieldLogin);
		layout.putConstraint(SpringLayout.NORTH, registerButton, 0, SpringLayout.NORTH, loginButton);

		validate();
	}

	private void setComponentSize(JComponent component) {
		component.setMinimumSize(new Dimension(50, 30));
		component.setMaximumSize(new Dimension(200, 30));
		component.setPreferredSize(new Dimension(200, 30));
		add(component);
	}

	public String getPassword() {
		return new String(passwordFieldLogin.getPassword());
	}

	public void setAlertText(String text) {
		alertLabel.setText(text);
	}
}
