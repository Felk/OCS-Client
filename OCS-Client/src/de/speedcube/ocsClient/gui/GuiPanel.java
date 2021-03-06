package de.speedcube.ocsClient.gui;

import javax.swing.JPanel;
import javax.swing.text.html.StyleSheet;

import de.speedcube.ocsClient.UserList;
import de.speedcube.ocsUtilities.UserInfo;

public abstract class GuiPanel extends JPanel {

	private static final long serialVersionUID = 1L;

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
		style.addRule(".rank{color: red;} .status{color: yellow;} .time{color: #ffffff;} .system{color: #ff7f00;} .link{color: #3399FF;}");
		//a{color: #3399FF !important; text-decoration: none !important; } .status.a{color: #3399FF !important; text-decoration: none !important}; 
	}

	public static StyleSheet getTextAreaStyle() {
		return style;
	}

	public static String escapeHTML(String s) {
		return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String setLinks(String text) {
		String tlds = "de|com|info|net|at|org|ch|gov|us|to|cz";
		return text.replaceAll("\\s?(http://|https://)?(\\S+\\.(" + tlds + ")(/\\S*|/?))\\b", " <span class='link'><a href=http://$2>$2</a></span> ");
		//return text.replaceAll("\\s?(http://|https://)?(\\S+\\.(" + tlds + ")(/\\S*|/?))\\b", " <a href=http://$2>$2</a> ");
	}

	public static String convertTimeToString(long timeDelta) {
		int timeInMillis = (int) (timeDelta / 1000000);
		int seconds = timeInMillis / 1000;
		int millis = timeInMillis % 1000;

		int minutes = seconds / 60;
		int temp1 = millis / 10;
		seconds %= 60;

		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds) + "," + (temp1 < 10 ? "0" + temp1 : temp1);
	}
}
