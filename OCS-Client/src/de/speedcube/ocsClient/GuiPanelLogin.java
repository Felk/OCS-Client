package de.speedcube.ocsClient;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.Packet;
import de.speedcube.ocsUtilities.packets.PacketLoginError;
import de.speedcube.ocsUtilities.packets.PacketLogout;
import de.speedcube.ocsUtilities.packets.PacketRegistrationError;
import de.speedcube.ocsUtilities.packets.PacketRegistrationSuccess;

public class GuiPanelLogin extends GuiPanel {

	public JLabel alertLabel;
	private Client client;
	private OCSClient window;

	public JTextField usernameFieldLogin;
	public JPasswordField passwordFieldLogin;
	public JButton loginButton;

	public JTextField usernameFieldRegister;
	public JPasswordField passwordFieldRegister;
	public JButton registerButton;

	public GuiPanelLogin(Client client, OCSClient window) {
		this.client = client;
		this.window = window;

		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		setBounds(0, 0, window.getWidth(), window.getHeight());

		alertLabel = new JLabel();
		alertLabel.setText("");
		add(alertLabel);

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

		layout.putConstraint(SpringLayout.NORTH, alertLabel, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, alertLabel, 70, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, usernameFieldLogin, 90, SpringLayout.NORTH, this);
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

	/*private void setButtonStyle(JButton button) {
		button.setForeground(Color.red);
		button.setBackground(Color.decode("#444444"));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
	}*/

	public String getPassword() {
		return new String(passwordFieldLogin.getPassword());
	}

	public void setAlertText(String text) {
		alertLabel.setText(text);
	}

	public void processPackets() {
		ArrayList<Packet> packets = client.getData(Packet.LOGIN_PAGE_CHANNEL);

		for (Packet p : packets) {
			if (p instanceof PacketLoginError) {
				setAlertText(((PacketLoginError) p).msg);
			} else if (p instanceof PacketLogout) {
				window.removeAllGuis();
				window.loginPanel.setAlertText(((PacketLogout) p).msg);
				System.out.println(((PacketLogout) p).msg);
				window.addGui(this);
			} else if (p instanceof PacketRegistrationError) {
				window.removeAllGuis();
				window.addGui(window.loginPanel);
				window.loginPanel.setAlertText("Registration failed");
			} else if (p instanceof PacketRegistrationSuccess) {
				window.removeAllGuis();
				window.addGui(window.loginPanel);
				window.loginPanel.setAlertText(((PacketRegistrationSuccess) p).username + " registered");
			}
		}
	}

	public void enableButtons(boolean enable) {
		loginButton.setEnabled(enable);
		registerButton.setEnabled(enable);
	}
}
