package de.speedcube.ocsClient;

import de.speedcube.ocsClient.gui.*;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class PacketHandlerLogin extends PacketHandler {

	private GuiTabLogin loginTab;
	private GuiTabContainer tabContainer;

	public PacketHandlerLogin(Client client, GuiTabLogin loginTab, GuiTabContainer tabContainer) {
		super(client);
		this.tabContainer = tabContainer;
		this.loginTab = loginTab;
		this.tabContainer = tabContainer;
	}

	@Override
	public void processPacket(Packet p) {
		if (p instanceof PacketSalt) {
			PacketLogin passwordPacket = new PacketLogin();
			passwordPacket.password = loginTab.getPassword();
			passwordPacket.salt = ((PacketSalt) p).salt;
			client.sendPacket(passwordPacket);
		} else if (p instanceof PacketLoginSuccess) {
			OCSClient.userInfo = (PacketLoginSuccess) p;
			tabContainer.enableTabs();
			System.out.println("test");
		} else if (p instanceof PacketLoginError) {
			loginTab.setAlertText(SystemStrings.getString(((PacketLoginError) p).msg));
		} else if (p instanceof PacketLogout) {
			tabContainer.disableTabs();
			loginTab.setAlertText(SystemStrings.getString(((PacketLogout) p).msg));
		} else if (p instanceof PacketRegistrationError) {
			tabContainer.disableTabs();
			loginTab.setAlertText(SystemStrings.getString(((PacketRegistrationError) p).err));
		} else if (p instanceof PacketRegistrationSuccess) {
			//window.tabContainer.disableTabs();
			loginTab.setAlertText(SystemStrings.getString("reg.success", new String[] { ((PacketRegistrationSuccess) p).username }));
		}
	}
}
