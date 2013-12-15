package de.speedcube.ocsClient;

import javax.swing.JPanel;

import de.speedcube.ocsUtilities.UserInfo;

public abstract class GuiPanel extends JPanel {
	private static String textAreaStyle;

	public static void genTextAreaStyle(UserList userList) {
		StringBuilder styleBuffer = new StringBuilder();

		styleBuffer.append("<head><style type ='text/css'> body { background-color:#222233; color:#ffffff; font-family:Arial; padding:7px;} ");
		if (userList != null) {
			for (UserInfo userInfo : userList.usersMap.values()) {
				if (userInfo != null) {
					styleBuffer.append(".u" + userInfo.userID + "{color: " + Integer.toHexString(userInfo.color) + "; font-weight:bold;}");
				}
			}
		}

		styleBuffer.append(".rank{color: red;} .status{color: yellow;} .time{color: #ffffff;} .system{color: #ff7f00;}");
		styleBuffer.append("</style></head>");

		textAreaStyle = styleBuffer.toString();
	}

	public static String getTextAreaStyle() {
		return textAreaStyle;
	}
}
