package de.speedcube.ocsClient.gui;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.speedcube.ocsClient.SystemStrings;

public class GuiPartyTimer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JLabel timerLabel;
	public JButton okButton;
	public JButton extraTimeButton;
	public JButton dnfButton;
	public JButton backButton;

	public GuiPartyTimer(OCSWindow window) {
		setLayout(null);
		setBounds(0, 0, 300, 150);

		timerLabel = new JLabel();
		timerLabel.setBounds(0, 0, 300, 100);

		okButton = new JButton(SystemStrings.getString("system.label.party.ok"));
		okButton.setBounds(0, 100, 80, 20);
		okButton.setName("DefButton");

		extraTimeButton = new JButton(SystemStrings.getString("system.label.party.+2"));
		extraTimeButton.setBounds(100, 100, 80, 20);
		extraTimeButton.setName("DefButton");

		dnfButton = new JButton(SystemStrings.getString("system.label.party.dnf"));
		dnfButton.setBounds(200, 100, 80, 20);
		dnfButton.setName("DefButton");

		backButton = new JButton(SystemStrings.getString("system.label.back"));
		backButton.setBounds(200, 130, 80, 20);
		backButton.setName("DefButton");

		setText(convertTimeToString(0));

		//addKeyListener(new TimerStartKeyListener(this));
		timerLabel.setBorder(new LineBorder(Color.red));
		timerLabel.setFocusable(true);

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
				timerLabel.setBorder(new LineBorder(Color.red));
			}

			@Override
			public void focusGained(FocusEvent e) {
				timerLabel.setBorder(new LineBorder(Color.green));
			}
		});

		add(timerLabel);
		add(okButton);
		add(extraTimeButton);
		add(dnfButton);
		add(backButton);

		validate();
	}

	public void startTimer() {

	}

	public void stopTimer() {

	}

	public void setText(String text) {
		timerLabel.setText("<html><p style='font-size:72pt;'>" + text + "</p></html>");
	}

	public void reset() {
		setText(convertTimeToString(0));
	}

	public void enableButtons() {
		// TODO Auto-generated method stub

	}
}
