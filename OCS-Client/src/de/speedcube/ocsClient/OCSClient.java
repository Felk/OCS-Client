package de.speedcube.ocsClient;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class OCSClient extends JFrame {

	public static final String version = "0";
	public Client client;
	public PacketLoginSuccess userInfo = null;
	public boolean disconnected = false;

	public Object receiveNotify;

	public UserList userList;

	public GuiPanelChat chatPanel;
	public GuiPanelLogin loginPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelTimer timerPanel;

	public OCSClient(String adress) {
		receiveNotify = new Object();

		client = new Client();

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

		userList = new UserList();

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
					removeAllGuis();
					addGui(chatPanel);
					addGui(userlistPanel);
					addGui(timerPanel);
				} else if (p instanceof PacketUserlist) {
					userlistPanel.updateUserlist((PacketUserlist) p);
				} else if (p instanceof PacketUserInfo) {
					userList.addUsers((PacketUserInfo) p);
					userlistPanel.updateUserlist();
					chatPanel.setTextField();
				} else if (p instanceof PacketDisconnect) {
					loginPanel.setAlertText(((PacketDisconnect) p).msg);
					disconnected = true;
				}
			}

			loginPanel.processPackets();
			chatPanel.processPackets();

			if (!client.connected) {
				removeAllGuis();
				addGui(loginPanel);
				loginPanel.enableButtons(false);
				if (!disconnected) loginPanel.setAlertText(SystemStrings.getString("system.connection_lost"));
			}
		}
	}

	public void removeAllGuis() {
		remove(loginPanel);
		remove(chatPanel);
		remove(userlistPanel);
		remove(timerPanel);
	}

	public void addGui(GuiPanel gui) {
		add(gui);
		validate();
		repaint();
	}

	public void setupWindow() {
		setTitle(SystemStrings.getString("system.title"));
		setLayout(null);

		//set OS style
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setSize(820, 600);
		getContentPane().setBackground(Color.decode("#444444"));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		loginPanel = new GuiPanelLogin(client, this);
		userlistPanel = new GuiPanelUserlist(client, this);
		chatPanel = new GuiPanelChat(client, this);
		timerPanel = new GuiPanelTimer(client, this);

		add(loginPanel);
		/*add(chatPanel);
		add(userlistPanel);
		add(timerPanel);*/
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
