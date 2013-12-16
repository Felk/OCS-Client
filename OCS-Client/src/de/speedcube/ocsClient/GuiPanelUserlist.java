package de.speedcube.ocsClient;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.UserInfo;
import de.speedcube.ocsUtilities.Userranks;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	public JEditorPane userlist;
	private HTMLEditorKit htmlEditor;
	public JScrollPane userlistScrollPane;
	private OCSClient window;
	private PacketUserlist userlistPacket;

	public GuiPanelUserlist(Client client, OCSClient window) {
		this.window = window;

		setLayout(null);
		setBounds(500, 0, 300, 250);

		userlist = new JEditorPane();
		userlist.setBounds(0, 0, 300, 250);
		userlist.setEditable(false);
		htmlEditor = new HTMLEditorKit();
		userlist.setEditorKit(htmlEditor);
		userlist.addHyperlinkListener(new HyperlinkListener() {
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

		userlistScrollPane = new JScrollPane(userlist);
		userlistScrollPane.setBounds(userlist.getBounds());

		add(userlistScrollPane);

		validate();
	}

	public void updateUserlist(PacketUserlist userlistPacket) {
		this.userlistPacket = userlistPacket;

		synchronized (userlist) {
			StringBuilder textBuffer = new StringBuilder();
			genTextAreaStyle(window.userList);
			htmlEditor.setStyleSheet(getTextAreaStyle());
			userlist.setDocument(htmlEditor.createDefaultDocument());

			textBuffer.append("<html><body>");

			for (int i : userlistPacket.userIds) {
				UserInfo userInfo = window.userList.getUserInfoByID(i);
				if (userInfo != null) {
					textBuffer.append("<br><span class ='u" + userInfo.userID + "'>" + userInfo.username + "</span>");
					if (userInfo.rank > Userranks.HIGH) textBuffer.append(" <span class ='rank'>[" + Userranks.getRankString(userInfo.rank) + "]</span>");
					String status = escapeHTML(userInfo.status);
					if (userInfo.status != null && !userInfo.status.equals("")) textBuffer.append(" <span class ='status'>" + (userInfo.rank >= Userranks.HIGH ? setLinks(status) : status) + "</span>");
				}
			}
			textBuffer.append("</body></html>");
			userlist.setText(textBuffer.toString());
			((DefaultCaret) userlist.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}

	/*public String getTextAreaStyle() {
		StringBuilder styleBuffer = new StringBuilder();

		styleBuffer.append("<head><style type ='text/css'> body { background-color:#222233; color:#ffffff; font-family:Arial; padding:7px;} ");
		if (userlistPacket != null) {
			for (int i : userlistPacket.userIds) {
				UserInfo userInfo = window.userList.getUserInfoByID(i);
				if (userInfo != null) {
					styleBuffer.append(".u" + userInfo.userID + "{color: " + Integer.toHexString(userInfo.color) + "; font-weight:bold;}");
				}
			}
		}

		styleBuffer.append(".rank{color: red;} .status{color: yellow;} .time{color: #ffffff;} .system{color: #ff7f00;}");
		styleBuffer.append("</style></head>");

		return styleBuffer.toString();
	}*/

	public void updateUserlist() {
		updateUserlist(userlistPacket);
	}
}
