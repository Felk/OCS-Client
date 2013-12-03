package de.speedcube.ocsClient;

import java.util.ArrayList;

import javax.swing.JFrame;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public class OCSClient extends JFrame {

	public static final String version = "0";
	public Client client;
	public String username = "undefined";

	public Object receiveNotify;
	public GuiPanelChat chatPanel;
	public GuiPanelLogin loginPanel;
	public GuiPanelUserlist userlistPanel;
	public GuiPanelTimer timerPanel;

	public OCSClient() {
		receiveNotify = new Object();
		client = new Client("felk.servegame.com", 34543, receiveNotify);

		setupWindow();
		boolean running = true;

		while (running) {
			synchronized (receiveNotify) {
				try {
					receiveNotify.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			ArrayList<Packet> packets = client.getData(0);

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
					add(chatPanel);
					add(userlistPanel);
				} else if (p instanceof PacketUserlist) {
					userlistPanel.updateUserlist((PacketUserlist) p);
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

	public void setupWindow() {
		setTitle("OCS");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		loginPanel = new GuiPanelLogin(client, this);
		chatPanel = new GuiPanelChat(client, this);
		userlistPanel = new GuiPanelUserlist(client, this);
		timerPanel = new GuiPanelTimer(client, this);

		add(loginPanel);
		//add(timerPanel);
		validate();
	}

	public static void main(String[] args) {
		new OCSClient();
	}
}
