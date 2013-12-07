package de.speedcube.ocsClient;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.speedcube.ocsClient.network.Client;

public class GuiPanelTimer extends GuiPanel {

	private Client client;
	public JLabel timerLabel;

	public GuiPanelTimer(Client client, OCSClient window) {
		this.client = client;

		setLayout(null);
		setBounds(0, 450, 300, 100);

		timerLabel = new JLabel();
		timerLabel.setBounds(0, 0, 300, 100);
		setText("00:00,00");

		addKeyListener(new TimerStartKeyListener(client, this));
		setFocusable(true);
		setBorder(new LineBorder(Color.red));

		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setBorder(new LineBorder(Color.red));
			}

			@Override
			public void focusGained(FocusEvent e) {
				setBorder(new LineBorder(Color.green));
			}
		});

		add(timerLabel);

		validate();
	}

	public void startTimer() {

	}

	public void stopTimer() {

	}

	public void setText(String text) {
		timerLabel.setText("<html><p style='font-size:72pt;'>" + text + "</p></html>");
	}
}
