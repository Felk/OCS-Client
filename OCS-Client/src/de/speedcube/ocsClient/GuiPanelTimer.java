package de.speedcube.ocsClient;

import javax.swing.JButton;
import javax.swing.JLabel;

import de.speedcube.ocsClient.network.Client;

public class GuiPanelTimer extends GuiPanel {

	public JLabel timerLabel;
	public JButton timerStartButton;
	

	public GuiPanelTimer(Client client, OCSClient window) {
		setLayout(null);
		setBounds(0, 0, window.getWidth(), window.getHeight());

		timerLabel = new JLabel();
		timerLabel.setBounds(0, 0, 800, 200);
		setText("00:00,00");

		timerStartButton = new JButton();
		timerStartButton.setText("Start");
		timerStartButton.setBounds(0, 200, 200, 50);
		TimerStartButtonListener timerStartButtonListener = new TimerStartButtonListener(client, this);
		timerStartButton.addActionListener(timerStartButtonListener);

		add(timerLabel);
		add(timerStartButton);

		validate();
	}

	public void setText(String text) {
		timerLabel.setText("<html><p style='font-size:72pt;'>" + text + "</p></html>");
	}
}
