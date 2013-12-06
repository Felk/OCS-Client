package de.speedcube.ocsClient;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketUserlist;

public class GuiPanelUserlist extends GuiPanel {

	public JEditorPane userlist;
	public JScrollPane userlistScrollPane;
	private OCSClient window;
	private PacketUserlist userlistPacket;

	public GuiPanelUserlist(Client client, OCSClient window) {
		this.window = window;

		setLayout(null);
		setBounds(500, 0, 300, 400);

		userlist = new JEditorPane();
		userlist.setBounds(0, 0, 300, 400);
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

		synchronized (userlist) {
			StringBuilder textBuffer = new StringBuilder();
			textBuffer.append("<html>" + getTextAreaStyle());

			for (int i : userlistPacket.userIds) {
				textBuffer.append("<br>");
				textBuffer.append(window.userList.getUserNameByID(i));
			}
			textBuffer.append("</html>");
			userlist.setText(textBuffer.toString());
			JScrollBar vbar = userlistScrollPane.getVerticalScrollBar();
			((DefaultCaret) userlist.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		}
	}

	private String getTextAreaStyle() {
		return "<head><style type='text/css'>body { background-color:#222233; color:#ffffff;}</style></head>";
	}

	public void updateUserlist() {
		updateUserlist(userlistPacket);
	}
}
