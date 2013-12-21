package de.speedcube.ocsClient;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.Packet;
import de.speedcube.ocsUtilities.packets.PacketChat;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;
import de.speedcube.ocsUtilities.packets.PacketSystemMessage;

public class GuiPanelChat extends GuiPanel {

	private Client client;
	public JEditorPane chatArea;
	private HTMLEditorKit htmlEditor;
	public JScrollPane chatScrollPane;
	public JTextField chatField;
	public JButton chatButton;
	public JCheckBox soundCheckbox;
	public OCSClient window;
	private AudioFile newMsgSound;

	public ArrayList<String> chatMessages;

	public GuiPanelChat(Client client, OCSClient window) {
		this.client = client;
		this.window = window;
		newMsgSound = new AudioFile("/newMsgSound3.wav");
		chatMessages = new ArrayList<String>();

		setLayout(null);

		setBounds(0, 0, 400, 450);

		chatArea = new JEditorPane();
		chatArea.setBounds(0, 0, 400, 400);
		chatArea.setEditable(false);
		htmlEditor = new HTMLEditorKit();
		chatArea.setEditorKit(htmlEditor);

		//link listener to open links
		chatArea.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setBounds(chatArea.getBounds());

		chatField = new JTextField();
		chatField.setBounds(0, 400, 300, 30);
		chatField.addKeyListener(new ChatKeyListener(this));
		chatField.setName("ChatTextfield");

		chatButton = new JButton();
		chatButton.setBounds(300, 400, 100, 30);
		chatButton.setText(SystemStrings.getString("system.label.chat"));
		chatButton.setName("ChatButton");

		ChatButtonListener chatButtonListener = new ChatButtonListener(this);
		chatButton.addActionListener(chatButtonListener);

		soundCheckbox = new JCheckBox(SystemStrings.getString("system.label.activate_sound"));
		soundCheckbox.setBounds(0, 430, 300, 20);
		soundCheckbox.setSelected(true);

		add(chatScrollPane);
		add(chatField);
		add(chatButton);
		add(soundCheckbox);

		genTextAreaStyle(window.userList);
		chatArea.setText("<html>" + getTextAreaStyle() + "<body>willkommen im OCS 1.0</body></html>");
		validate();
	}

	public void addChatMessage(PacketChatBroadcast message) {
		SimpleDateFormat chatTime = new SimpleDateFormat("H:mm");
		String timeString = chatTime.format(new Date(message.timestamp));
		chatMessages.add("<span class ='time'>" + timeString + "</span>  <span class ='u" + message.userId + "'>" + window.userList.getUserNameByID(message.userId) + "</span> - " + setLinks(escapeHTML(message.text)));
		setTextField();
		if (message.userId != window.userInfo.userID && soundCheckbox.isSelected()) {
			newMsgSound.play();
		}
	}

	public void addSystemMessage(PacketSystemMessage message) {
		SimpleDateFormat chatTime = new SimpleDateFormat("H:mm");
		String timeString = chatTime.format(new Date(message.timestamp));
		chatMessages.add("<span class ='time'>" + timeString + "</span>  <span class ='system'>" + SystemStrings.getString(message.msg, message.values) + "</span>");
		setTextField();
		if (soundCheckbox.isSelected()) newMsgSound.play();
	}

	public void setTextField() {
		synchronized (chatArea) {
			StringBuilder textBuffer = new StringBuilder();
			htmlEditor.setStyleSheet(getTextAreaStyle());
			chatArea.setDocument(htmlEditor.createDefaultDocument());
			textBuffer.append("<html>");

			for (String s : chatMessages) {
				textBuffer.append("<br>");
				textBuffer.append(s);
			}
			textBuffer.append("</html>");

			chatArea.setText(textBuffer.toString());
			((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}

	public void processPackets() {
		ArrayList<Packet> packets = client.getData(Packet.CHAT_CHANNEL);

		for (Packet p : packets) {
			if (p instanceof PacketChatBroadcast) {
				addChatMessage((PacketChatBroadcast) p);
			} else if (p instanceof PacketSystemMessage) {
				addSystemMessage((PacketSystemMessage) p);
			}
		}
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
