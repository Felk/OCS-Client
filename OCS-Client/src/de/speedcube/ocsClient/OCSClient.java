package de.speedcube.ocsClient;

import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class OCSClient extends JFrame {

	public static final String version = "0.85.1";
	public Client client;
	public PacketLoginSuccess userInfo = null;
	public boolean disconnected = false;

	public Object receiveNotify;

	public UserList userList;

	public GuiPanelChat chatPanel;
	public GuiPanelLogin loginPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelTimer timerPanel;
	public GuiPanelChatContainer chatPanelContainer;

	public GuiTabContainer tabContainer;

	public OCSClient(String adress) {
		receiveNotify = new Object();

		client = new Client();

		userList = new UserList();
		setupWindow();

		loginPanel.enableButtons(false);
		loginPanel.setAlertText(SystemStrings.getString("system.connecting"));
		client.connect(adress, 34543, receiveNotify);

		if (client.connected) {
			loginPanel.setAlertText(SystemStrings.getString("system.connected"));
			loginPanel.enableButtons(true);
		} else {
			loginPanel.setAlertText(SystemStrings.getString("system.connection_failed"));
			return;
		}

		boolean running = true;

		while (running) {
			ArrayList<Packet> packets;
			packets = client.getData(0);

			if (packets.size() == 0) {

				synchronized (receiveNotify) {
					try {
						receiveNotify.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				packets = client.getData(Packet.DEFAULT_CHANNEL);
			}

			for (Packet p : packets) {
				if (p instanceof PacketSalt) {
					PacketLogin passwordPacket = new PacketLogin();
					passwordPacket.password = loginPanel.getPassword();
					passwordPacket.salt = ((PacketSalt) p).salt;
					client.sendPacket(passwordPacket);
				} else if (p instanceof PacketLoginSuccess) {
					userInfo = (PacketLoginSuccess) p;
					tabContainer.enableTabs();
				} else if (p instanceof PacketUserlist) {
					userlistPanel.updateUserlist((PacketUserlist) p);
				} else if (p instanceof PacketUserInfo) {
					userList.addUsers((PacketUserInfo) p);
					userlistPanel.updateUserlist();
					chatPanel.updateChatAreasContent();
				} else if (p instanceof PacketDisconnect) {
					loginPanel.setAlertText(((PacketDisconnect) p).msg);
					disconnected = true;
				}
			}

			loginPanel.processPackets();
			chatPanel.processPackets();

			if (!client.connected) {
				tabContainer.disableTabs();
				loginPanel.enableButtons(false);
				if (!disconnected) loginPanel.setAlertText(SystemStrings.getString("system.connection_lost"));
			}
		}
	}

	private void setupStyle() {

		try {
			SynthLookAndFeel laf = new SynthLookAndFeel();
			laf.load(getClass().getResourceAsStream("/laf.xml"), getClass());
			UIManager.setLookAndFeel(laf);
		} catch (ParseException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public void setupWindow() {
		setTitle(SystemStrings.getString("system.title", new String[] { version }));
		//setLayout(null);
		setupStyle();

		//set OS style
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/

		setSize(820, 600);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		loginPanel = new GuiPanelLogin(client, this);
		userlistPanel = new GuiPanelUserlist(client, this);
		chatPanel = new GuiPanelChat(client, this);
		timerPanel = new GuiPanelTimer(client, this);
		chatPanelContainer = new GuiPanelChatContainer(this, chatPanel, userlistPanel);

		tabContainer = new GuiTabContainer(this, loginPanel, chatPanelContainer);

		add(tabContainer);

		validate();
		repaint();
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
