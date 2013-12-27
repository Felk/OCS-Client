package de.speedcube.ocsClient.chat;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.AudioFile;
import de.speedcube.ocsClient.GuiPanel;
import de.speedcube.ocsClient.SystemStrings;
import de.speedcube.ocsClient.UserList;
import de.speedcube.ocsUtilities.packets.PacketChatBroadcast;
import de.speedcube.ocsUtilities.packets.PacketSystemMessage;

public class ChatHistory {
	public ArrayList<Message> messages;

	public String name;
	private UserList userList;
	private JEditorPane chatArea;
	private HTMLEditorKit htmlEditor;
	public JScrollPane chatScrollPane;

	public ChatHistory(String name, UserList userList) {
		this.name = name;
		this.userList = userList;

		chatArea = new JEditorPane();

		chatArea = new JEditorPane();
		chatArea.setBounds(0, 0, 400, 400);
		chatArea.setEditable(false);
		htmlEditor = new HTMLEditorKit();
		chatArea.setEditorKit(htmlEditor);

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

		messages = new ArrayList<Message>();
	}

	public void addChatMessage(PacketChatBroadcast message, int userID, boolean playSound, AudioFile newMsgSound) {
		messages.add(new ChatMessage(message.timestamp, message.text, message.userId));
		setTextField();
		if (message.userId != userID && playSound) {
			newMsgSound.play();
		}
	}

	public void addSystemMessage(PacketSystemMessage message, boolean playSound, AudioFile newMsgSound) {
		messages.add(new SystemMessage(message.timestamp, SystemStrings.getString(message.msg, message.values)));
		setTextField();
		if (playSound) newMsgSound.play();
	}

	public void setTextField() {
		StringBuilder textBuffer = new StringBuilder();
		htmlEditor.setStyleSheet(GuiPanel.getTextAreaStyle());
		chatArea.setDocument(htmlEditor.createDefaultDocument());

		//synchronized (chatScrollPane) {
		//synchronized (chatArea) {

		textBuffer.append("<html>");

		for (Message s : messages) {
			textBuffer.append("<br>");
			textBuffer.append(s.toString(userList));
		}
		textBuffer.append("</html>");

		chatArea.setText(textBuffer.toString());
		((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//}
		//}
	}
}
