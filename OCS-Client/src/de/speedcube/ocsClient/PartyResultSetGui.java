package de.speedcube.ocsClient;

import javax.swing.JLabel;
import javax.swing.ToolTipManager;

import de.speedcube.ocsClient.gui.GuiPanel;
import de.speedcube.ocsUtilities.PartyResultSet;

public class PartyResultSetGui {
	public JLabel userLabel;
	private PartyResultSet resultSet;
	private UserList userlist;

	public PartyResultSetGui(UserList userlist, PartyResultSet resultSet) {
		this.userlist = userlist;
		userLabel = new JLabel();
		setResultSet(resultSet);
	}

	private void setResultSet(PartyResultSet resultSet) {
		this.resultSet = resultSet;
		recalcLabel();
	}

	private void recalcLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append(userlist.getUserNameByID(resultSet.getUserID()));

		sb.append(" AVG: " + GuiPanel.convertTimeToString(resultSet.getAverage()));
		userLabel.setText(sb.toString());
		userLabel.setToolTipText(getTooltipString());

		ToolTipManager.sharedInstance().registerComponent(userLabel);
	}

	private String getTooltipString() {
		StringBuilder sb = new StringBuilder();
		if (resultSet.getTimes() != null) {
			sb.append("<html>");
			for (int i = 0; i < resultSet.getTimes().length; i++) {
				sb.append(GuiPanel.convertTimeToString(resultSet.getTimes()[i]));
				if ((i % 10) == 9) {
					sb.append("<br>");
				} else if (i < resultSet.getTimes().length - 1) {
					sb.append(" | ");
				}

			}
			sb.append("</html>");
		}
		return sb.toString();
	}
}
