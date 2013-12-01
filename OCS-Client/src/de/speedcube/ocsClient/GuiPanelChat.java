package de.speedcube.ocsClient;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketChat;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;

public class GuiPanelChat extends GuiPanel {

	private Client client;
	public JEditorPane chatArea;
	public JTextField chatField;
	public JButton chatButton;
	public OCSClient window;

	public ArrayList<String> chatMessages;

	public GuiPanelChat(Client client, OCSClient window) {
		this.client = client;
		this.window = window;
		chatMessages = new ArrayList<String>();

		setLayout(null);
		setBounds(0, 0, 500, window.getHeight());

		chatArea = new JEditorPane();
		chatArea.setBounds(0, 0, 400, 400);
		chatArea.setEditable(false);
		HTMLEditorKit htmlKit = new HTMLEditorKit();
		chatArea.setEditorKit(htmlKit);

		chatField = new JTextField();
		chatField.setBounds(0, 400, 300, 30);
		chatField.addKeyListener(new ChatTextFieldListener(this));

		chatButton = new JButton();
		chatButton.setBounds(300, 400, 100, 30);
		chatButton.setText("chat");

		ChatButtonListener chatButtonListener = new ChatButtonListener(this);
		chatButton.addActionListener(chatButtonListener);

		add(chatArea);
		add(chatField);
		add(chatButton);
		//setTextField();
		chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void addChatMessage(PacketChatBroadcast message) {
		chatMessages.add("<b>" + window.userlistPanel.getUserNameByID(message.userId) + "</b> - " + message.text);
		setTextField();
	}

	public void setTextField() {
		StringBuilder textBuffer = new StringBuilder();
		textBuffer.append("<html>" + getTextAreaStyle());

		for (String s : chatMessages) {
			textBuffer.append("<br>");
			textBuffer.append(s);
		}
		textBuffer.append("</html>");
		chatArea.setText(textBuffer.toString());
		System.out.println(chatArea.getText());
	}

	private String getTextAreaStyle() {
		return "<head><style type='text/css'>body { background-color:#222233; color:#ffffff;}</style></head>";
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
