package de.speedcube.ocsClient;

import de.speedcube.ocsClient.gui.OCSWindow;
import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.Packet;
import de.speedcube.ocsUtilities.packets.PacketLoginSuccess;

public class OCSClient {

	public static final String version = "0.85.1";
	public Client client;
	OCSWindow window;

	public boolean disconnected = false;

	public Object receiveNotify;

	public static PacketLoginSuccess userInfo = null;
	public static UserList userList;

	public PacketHandlerDefault packetHandlerDefault;
	public PacketHandlerLogin packetHandlerLogin;
	public PacketHandlerChat packetHandlerChat;
	public PacketHandlerParty packetHandlerParty;

	public OCSClient(String adress) {
		receiveNotify = new Object();
		client = new Client();
		userList = new UserList();

		window = new OCSWindow();
		window.setupWindow(version);
		initListeners();

		window.loginTab.enableButtons(false);
		window.loginTab.setAlertText(SystemStrings.getString("system.connecting"));
		client.connect(adress, 34543, receiveNotify);

		if (client.connected) {
			window.loginTab.setAlertText(SystemStrings.getString("system.connected"));
			window.loginTab.enableButtons(true);
		} else {
			window.loginTab.setAlertText(SystemStrings.getString("system.connection_failed"));
			return;
		}

		boolean running = true;

		packetHandlerDefault = new PacketHandlerDefault(client, this, window);
		packetHandlerLogin = new PacketHandlerLogin(client, window.loginTab, window.tabContainer);
		packetHandlerChat = new PacketHandlerChat(client, window.chatTab.chatPanel);
		packetHandlerParty = new PacketHandlerParty(client, window.chatTab.partyContainer);

		while (running) {

			if (!client.isDataAvailable()) {
				synchronized (receiveNotify) {
					try {
						receiveNotify.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			packetHandlerDefault.handlePackets(Packet.DEFAULT_CHANNEL);
			packetHandlerLogin.handlePackets(Packet.LOGIN_PAGE_CHANNEL);
			packetHandlerChat.handlePackets(Packet.CHAT_CHANNEL);
			packetHandlerParty.handlePackets(Packet.PARTY_CHANNEL);

			if (!client.connected) {
				window.tabContainer.disableTabs();
				window.loginTab.enableButtons(false);
				if (!disconnected) window.loginTab.setAlertText(SystemStrings.getString("system.connection_lost"));
			}
		}
	}

	private void initListeners() {
		window.loginTab.loginButton.addActionListener(new LoginButtonListener(client, window.loginTab.usernameFieldLogin, window.loginTab.passwordFieldLogin, false));
		window.loginTab.registerButton.addActionListener(new LoginButtonListener(client, window.loginTab.usernameFieldRegister, window.loginTab.passwordFieldRegister, true));
		window.chatTab.setLinkListener(new OCSLinkListener(client));
	}

	public static void main(String[] args) {
		String adress = "picocom.net";

		if (args.length >= 2) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-a") && i + 1 < args.length) {
					adress = args[i + 1];
				}
			}
		}

		new OCSClient(adress);
	}
}
