package de.speedcube.ocsClient;

import java.util.ArrayList;
import java.util.HashMap;

import de.speedcube.ocsUtilities.UserInfo;
import de.speedcube.ocsUtilities.packets.PacketUserInfo;

public class UserList {
	public HashMap<Integer, UserInfo> usersMap;

	public UserList() {
		usersMap = new HashMap<Integer, UserInfo>();
	}

	public void addUser(UserInfo info) {
		usersMap.put(info.userID, info);
	}

	public void addUsers(PacketUserInfo infos) {
		ArrayList<UserInfo> userInfos = infos.getUserInfos();
		for (UserInfo info : userInfos) {
			usersMap.put(info.userID, info);
		}
	}

	public String getUserNameByID(int id) {
		if (usersMap.get(id) != null) {
			return usersMap.get(id).username;
		} else return "null";

	}

	public UserInfo getUserInfoByID(int id) {
		return usersMap.get(id);
	}
}
