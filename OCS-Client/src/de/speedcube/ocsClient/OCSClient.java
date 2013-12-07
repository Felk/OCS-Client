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
	public String username = "undefined";

	public Object receiveNotify;

	public UserList userList;

	public GuiPanelChat chatPanel;
	public GuiPanelLogin loginPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelTimer timerPanel;

	public OCSClient() {
		receiveNotify = new Object();

		client = new Client();

		setupWindow();
		loginPanel.enableButtons(false);
		loginPanel.setAlertText("Connecting...");
		client.connect("felk.servegame.com", 34543, receiveNotify);

		if (client.connected) {
			loginPanel.setAlertText("Connected");
			loginPanel.enableButtons(true);
		} else {
			loginPanel.setAlertText("failed to connect!");
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
				packets = client.getData(0);
			}

			for (Packet p : packets) {
				if (p instanceof PacketChatBroadcast) {
					chatPanel.addChatMessage(((PacketChatBroadcast) p));
				} else if (p instanceof PacketSalt) {
					PacketLogin passwordPacket = new PacketLogin();
					passwordPacket.password = loginPanel.getPassword();
					passwordPacket.salt = ((PacketSalt) p).salt;
					client.sendPacket(passwordPacket);
				} else if (p instanceof PacketLoginSuccess) {
					username = ((PacketLoginSuccess) p).username;
					removeAllGuis();
					addGui(chatPanel);
					addGui(userlistPanel);
					addGui(timerPanel);
				} else if (p instanceof PacketUserlist) {
					userlistPanel.updateUserlist((PacketUserlist) p);
				} else if (p instanceof PacketLogout) {
					removeAllGuis();
					addGui(loginPanel);
					loginPanel.setAlertText(((PacketLogout) p).msg);
				} else if (p instanceof PacketUserInfo) {
					userList.addUsers((PacketUserInfo) p);
					userlistPanel.updateUserlist();
					chatPanel.setTextField();
				}
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
		setTitle("OCS");
		setLayout(null);

		//set OS style
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setSize(800, 600);
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
		new OCSClient();
	}
}
