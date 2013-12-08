package de.speedcube.ocsClient;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.UserInfo;
import de.speedcube.ocsUtilities.Userranks;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	public JEditorPane userlist;
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
		HTMLEditorKit htmlKit = new HTMLEditorKit();
		userlist.setEditorKit(htmlKit);

		userlistScrollPane = new JScrollPane(userlist);
		userlistScrollPane.setBounds(userlist.getBounds());

		add(userlistScrollPane);

		validate();
	}

	public void updateUserlist(PacketUserlist userlistPacket) {
		this.userlistPacket = userlistPacket;

		//synchronized (userlist) {
		StringBuilder textBuffer = new StringBuilder();
		textBuffer.append("<html>" + getTextAreaStyle() + "<body>");

		for (int i : userlistPacket.userIds) {
			UserInfo userInfo = window.userList.getUserInfoByID(i);
			if (userInfo != null) {
				textBuffer.append("<br><span class ='u" + userInfo.userID + "'>" + userInfo.username + "</span>");
				textBuffer.append(" - <span class ='rank'>[" + Userranks.getRankString(userInfo.rank) + "]</span>");
				textBuffer.append(" - <span class ='status'>" + userInfo.status + "</span>");
			}
		}
		textBuffer.append("</body></html>");
		userlist.setText(textBuffer.toString());
		((DefaultCaret) userlist.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//}
	}

	public String getTextAreaStyle() {
		StringBuilder styleBuffer = new StringBuilder();

		styleBuffer.append("<head><style type ='text/css'> body { background-color:#222233; color:#ffffff; font-family:Arial; padding:3px;} ");
		if (userlistPacket != null) {
			for (int i : userlistPacket.userIds) {
				UserInfo userInfo = window.userList.getUserInfoByID(i);
				if (userInfo != null) {
					styleBuffer.append(".u" + userInfo.userID + "{color: " + Integer.toHexString(userInfo.color) + "; font-weight:bold;}");
				}
			}
		}

		styleBuffer.append(".rank{color: red;} .status{color: yellow;}");
		styleBuffer.append("</style></head>");

		return styleBuffer.toString();
	}

	public void updateUserlist() {
		updateUserlist(userlistPacket);
	}
}
