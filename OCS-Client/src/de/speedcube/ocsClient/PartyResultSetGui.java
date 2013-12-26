package de.speedcube.ocsClient;

import javax.swing.JLabel;

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
		sb.append(" - ");
		sb.append(resultSet.getAverage());
		userLabel.setText(sb.toString());
	}
}
