package de.speedcube.ocsClient;

import javax.swing.JPanel;
import javax.swing.text.html.StyleSheet;

import de.speedcube.ocsUtilities.UserInfo;

public abstract class GuiPanel extends JPanel {
	private static StyleSheet style;

	public static void genTextAreaStyle(UserList userList) {
		style = new StyleSheet();
		style.addRule("body{font-size: 14pt; background-color:#222233; color:#ffffff; font-family:Arial; padding:7px;}");

		if (userList != null) {
			for (UserInfo userInfo : userList.usersMap.values()) {
				if (userInfo != null) {
					style.addRule(".u" + userInfo.userID + "{color: " + Integer.toHexString(userInfo.color) + "; font-weight:bold;}");
				}
			}
		}
		style.addRule(".rank{color: red;} .status{color: yellow;} .time{color: #ffffff;} .system{color: #ff7f00;} a{color: #3399FF !important; text-decoration: none !important; } .status.a{color: #3399FF !important; text-decoration: none !important; }");
	}

	public static StyleSheet getTextAreaStyle() {
		return style;
	}

	public static String escapeHTML(String s) {
		return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String setLinks(String text) {
		String tlds = "de|com|info|net|at|org|ch|gov|us|to|cz";
		return text.replaceAll("\\s?(http://|https://)?(\\S+\\.(" + tlds + ")(/\\S*|/?))\\b", " <a href=http://$2>$2</a> ");
	}
}
