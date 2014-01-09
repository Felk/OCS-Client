package de.speedcube.ocsClient.gui;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.OCSClient;
import de.speedcube.ocsUtilities.UserInfo;
import de.speedcube.ocsUtilities.Userranks;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JEditorPane userlist;
	private HTMLEditorKit htmlEditor;
	public JScrollPane userlistScrollPane;

	private PacketUserlist userlistPacket;

	public GuiPanelUserlist() {

		setLayout(null);
		setBounds(500, 0, 300, 250);

		userlist = new JEditorPane();
		userlist.setBounds(0, 0, 300, 250);
		userlist.setEditable(false);
		htmlEditor = new HTMLEditorKit();
		userlist.setEditorKit(htmlEditor);

		userlistScrollPane = new JScrollPane(userlist);
		userlistScrollPane.setBounds(userlist.getBounds());

		add(userlistScrollPane);

		validate();
	}

	public void updateUserlist(PacketUserlist userlistPacket) {
		if (userlistPacket == null) return;
		this.userlistPacket = userlistPacket;

		synchronized (userlist) {
			StringBuilder textBuffer = new StringBuilder();
			genTextAreaStyle(OCSClient.userList);
			htmlEditor.setStyleSheet(getTextAreaStyle());
			userlist.setDocument(htmlEditor.createDefaultDocument());

			textBuffer.append("<html><body>");

			for (int i : userlistPacket.userIds) {
				UserInfo userInfo = OCSClient.userList.getUserInfoByID(i);
				if (userInfo != null) {
					textBuffer.append("<br><a href='u:" + userInfo.userID + "'><span class ='u" + userInfo.userID + "'>" + userInfo.username + "</span></a>");
					if (userInfo.rank > Userranks.HIGH) textBuffer.append(" <span class ='rank'>[" + Userranks.getString(userInfo.rank) + "]</span>");
					String status = escapeHTML(userInfo.status);
					if (userInfo.status != null && !userInfo.status.equals("")) textBuffer.append(" <span class ='status'>" + (userInfo.rank >= Userranks.HIGH ? setLinks(status) : status) + "</span>");
				}
			}
			textBuffer.append("</body></html>");
			userlist.setText(textBuffer.toString());
			((DefaultCaret) userlist.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}

	public void updateUserlist() {
		updateUserlist(userlistPacket);
	}

	public void reset() {
		userlistPacket = null;
	}
}
