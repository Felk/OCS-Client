package de.speedcube.ocsClient.gui;

import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.OCSLinkListener;
import de.speedcube.ocsClient.chat.Message;

public class GuiChatChannel {

	private JEditorPane chatArea;
	private HTMLEditorKit htmlEditor;
	public JScrollPane chatScrollPane;

	public GuiChatChannel(OCSLinkListener linkListener) {
		chatArea = new JEditorPane();
		chatArea.setEditable(false);
		htmlEditor = new HTMLEditorKit();
		chatArea.setEditorKit(htmlEditor);

		chatArea.addHyperlinkListener(linkListener);

		chatScrollPane = new JScrollPane(chatArea);
		chatScrollPane.setBounds(chatArea.getBounds());
	}

	public void setTextField(ArrayList<Message> messages) {
		StringBuilder textBuffer = new StringBuilder();
		htmlEditor.setStyleSheet(GuiPanel.getTextAreaStyle());
		chatArea.setDocument(htmlEditor.createDefaultDocument());

		//synchronized (chatScrollPane) {
		//synchronized (chatArea) {

		textBuffer.append("<html>");

		for (Message s : messages) {
			textBuffer.append("<br>");
			textBuffer.append(s.toString());
		}
		textBuffer.append("</html>");

		chatArea.setText(textBuffer.toString());
		((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//}
		//}
	}
}
