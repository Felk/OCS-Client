package de.speedcube.ocsClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketChat;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;

public class GuiPanelChat extends GuiPanel {

	private Client client;
	public JEditorPane chatArea;
	public JScrollPane chatScrollPane;
	public JTextField chatField;
	public JButton chatButton;
	public OCSClient window;

	public ArrayList<String> chatMessages;

	public GuiPanelChat(Client client, OCSClient window) {
		this.client = client;
		this.window = window;
		chatMessages = new ArrayList<String>();

		setLayout(null);

		setBounds(0, 0, 400, 430);

		chatArea = new JEditorPane();
		chatArea.setBounds(0, 0, 400, 400);
		chatArea.setEditable(false);
		HTMLEditorKit htmlKit = new HTMLEditorKit();
		chatArea.setEditorKit(htmlKit);

		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setBounds(chatArea.getBounds());

		chatField = new JTextField();
		chatField.setBounds(0, 400, 300, 30);
		chatField.addKeyListener(new ChatKeyListener(this));

		chatButton = new JButton();
		chatButton.setBounds(300, 400, 100, 30);
		chatButton.setText("chat");

		ChatButtonListener chatButtonListener = new ChatButtonListener(this);
		chatButton.addActionListener(chatButtonListener);

		add(chatScrollPane);
		add(chatField);
		add(chatButton);
		chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void addChatMessage(PacketChatBroadcast message) {
		SimpleDateFormat chatTime = new SimpleDateFormat("H:mm");
		String timerString = chatTime.format(new Date(message.timestamp));
		chatMessages.add(timerString + "  <span class ='u" + message.userId + "'>" + window.userList.getUserNameByID(message.userId) + "</span> - " + message.text);
		setTextField();
	}

	public void setTextField() {
		synchronized (chatArea) {
			StringBuilder textBuffer = new StringBuilder();
			textBuffer.append("<html>" + getTextAreaStyle());

			for (String s : chatMessages) {
				textBuffer.append("<br>");
				textBuffer.append(s);
			}
			textBuffer.append("</html>");
			chatArea.setText(textBuffer.toString());
			((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}

	public String getTextAreaStyle() {
		return window.userlistPanel.getTextAreaStyle();
	}

	public void sendChatMessage() {
		if (!chatField.getText().equals("")) {
			PacketChat chatPacket = new PacketChat();
			chatPacket.text = chatField.getText();
			chatField.setText("");

			client.sendPacket(chatPacket);
		}
	}
}
