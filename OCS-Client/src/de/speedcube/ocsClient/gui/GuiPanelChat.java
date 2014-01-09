package de.speedcube.ocsClient.gui;

import java.util.ArrayList;

import javax.swing.*;

import de.speedcube.ocsClient.*;
import de.speedcube.ocsClient.chat.ChatHistory;
import de.speedcube.ocsUtilities.Config;

public class GuiPanelChat extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JTabbedPane chatTabs;

	public JTextField chatField;
	public JButton chatButton;
	public JCheckBox soundCheckbox;
	public JButton leaveChannelButton;
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
		soundCheckbox.setBounds(0, 430, 100, 20);
		soundCheckbox.setSelected(true);

		leaveChannelButton = new JButton();
		leaveChannelButton.setBounds(250, 430, 150, 20);
		leaveChannelButton.setText(SystemStrings.getString("system.label.leaveChannel"));
		leaveChannelButton.setName("DefButton");

		chatTabs = new JTabbedPane();
		chatTabs.setBounds(0, 0, 400, 400);
		chatTabs.setName("ChatChannelTabs");

		add(chatTabs);
		add(chatField);
		add(chatButton);
		add(soundCheckbox);
		add(leaveChannelButton);

		genTextAreaStyle(OCSClient.userList);
		//chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void updateChatAreasTabs(ArrayList<ChatHistory> chatHistories) {
		chatTabs.removeAll();

		for (int i = 0; i < chatHistories.size(); i++) {
			ChatHistory ch = chatHistories.get(i);
			chatTabs.addTab(ch.name.split(Config.CHAT_CHANNEL_SEPARATOR)[0], ch.channelGui.chatScrollPane);
		}
	}

	public void reset() {
		chatField.setText("");
		chatTabs.removeAll();
	}
}
