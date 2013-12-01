package de.speedcube.ocsClient;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.speedcube.ocsClient.network.Client;

public class GuiPanelTimer extends GuiPanel {

	public JLabel timerLabel;

	public GuiPanelTimer(Client client, OCSClient window) {
		setLayout(null);
		setBounds(0, 0, window.getWidth(), window.getHeight());

		timerLabel = new JLabel();
		timerLabel.setBounds(0, 0, 800, 200);
		setText("00:00,00");

		add(timerLabel);

		validate();
	}

	public void setText(String text) {
		timerLabel.setText("<html><p style='font-size:72pt;'>" + text + "</p></html>");
	}
}
