package de.speedcube.ocsClient.gui;

import java.util.ArrayList;

import javax.swing.*;

import de.speedcube.ocsClient.*;
import de.speedcube.ocsClient.chat.ChatHistory;

public class GuiPanelChat extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JTabbedPane chatTabs;

	public JTextField chatField;
	public JButton chatButton;
	public JCheckBox soundCheckbox;
	public OCSWindow window;

	public GuiPanelChat(OCSWindow window) {
		this.window = window;

		setLayout(null);

		setBounds(0, 0, 400, 500);

		chatField = new JTextField();
		chatField.setBounds(0, 400, 300, 30);
		chatField.setName("ChatTextfield");

		chatButton = new JButton();
		chatButton.setBounds(300, 400, 100, 30);
		chatButton.setText(SystemStrings.getString("system.label.chat"));
		chatButton.setName("ChatButton");

		soundCheckbox = new JCheckBox(SystemStrings.getString("system.label.activate_sound"));
		soundCheckbox.setBounds(0, 430, 300, 20);
		soundCheckbox.setSelected(true);

		chatTabs = new JTabbedPane();
		chatTabs.setBounds(0, 0, 400, 400);
		chatTabs.setName("ChatChannelTabs");

		add(chatTabs);
		add(chatField);
		add(chatButton);
		add(soundCheckbox);

		genTextAreaStyle(OCSClient.userList);
		//chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void updateChatAreasTabs(ArrayList<ChatHistory> chatHistories) {
		chatTabs.removeAll();

		for (ChatHistory ch : chatHistories) {
			chatTabs.addTab(ch.name, ch.channelGui.chatScrollPane);
		}
	}
}
