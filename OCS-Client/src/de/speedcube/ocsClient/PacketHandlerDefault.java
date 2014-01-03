package de.speedcube.ocsClient;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.speedcube.ocsClient.gui.*;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class PacketHandlerDefault extends PacketHandler {

	private OCSClient ocsClient;
	private GuiPanelUserlist userlistPanel;
	private GuiTabLogin loginTab;
	private GuiPanelChat chatPanel;
	private GuiTabContainer tabContainer;

	public PacketHandlerDefault(Client client, OCSClient ocsClient, OCSWindow window) {
		super(client);
		this.ocsClient = ocsClient;
		this.userlistPanel = window.chatTab.userlistPanel;
		this.loginTab = window.loginTab;
		this.chatPanel = window.chatTab.chatPanel;
		this.tabContainer = window.tabContainer;

		initListeners();
	}

	@Override
	public void processPacket(Packet p) {
		if (p instanceof PacketUserlist) {
			userlistPanel.updateUserlist((PacketUserlist) p);
		} else if (p instanceof PacketUserInfo) {
			OCSClient.userList.addUsers((PacketUserInfo) p);
			userlistPanel.updateUserlist();
			chatPanel.updateChatAreasTabs(ocsClient.packetHandlerChat.chatRooms);
		} else if (p instanceof PacketDisconnect) {
			loginTab.setAlertText(((PacketDisconnect) p).msg);
			ocsClient.disconnected = true;
		}
	}

	private void initListeners() {

		tabContainer.tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabContainer.tabbedPane.getSelectedIndex() == 0) {
					sendLogout();
				}
			}
		});
	}

	public void sendLogout() {
		client.sendPacket(new PacketLogout());
	}
}
