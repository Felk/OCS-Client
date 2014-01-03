package de.speedcube.ocsClient.gui;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.speedcube.ocsClient.TimerStartKeyListener;

public class GuiPanelTimer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JLabel timerLabel;

	public GuiPanelTimer(OCSWindow window) {
		setLayout(null);
		setBounds(0, 450, 300, 100);

		timerLabel = new JLabel();
		timerLabel.setBounds(0, 0, 300, 100);
		setText("00:00,00");

		addKeyListener(new TimerStartKeyListener( this));
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
