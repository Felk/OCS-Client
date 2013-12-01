package de.speedcube.ocsClient.network;

import java.net.*;
import java.util.ArrayList;

import de.speedcube.ocsClient.OCSClient;
import de.speedcube.ocsUtilities.packets.*;

public class Client {
	private Socket socket;
	public boolean connected = false;
	SendThread sender;
	ReceiveThread receiver;

	public static final int CLIENT = 0;
	public static final int SERVER_CLIENT = 1;
	public int clientType = CLIENT;
	public PacketConnectionInfo connectionInfo;
	public boolean connectionInfoReceived = false;
	public boolean connectionInfoSent = false;
	private Object receiveNotify;
	//public ClientInformation clientInformation = null;
	public String salt;

	public String closeMessage = "";

	public Client(String adress, int port, Object receiveNotify) {
		this.receiveNotify = receiveNotify;
		try {
			socket = new Socket(adress, port);
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	public void init() {
		sender = new SendThread(socket);
		receiver = new ReceiveThread(socket, this, receiveNotify);

		sender.start();
		receiver.start();
	}

	public void setConnectionInfo(PacketConnectionInfo connectionInfo) {
		this.connectionInfo = connectionInfo;
		connectionInfoReceived = true;

		if (!connectionInfo.version.equals(OCSClient.version)) {
			stopClient();
			closeMessage = "incorrect version number";
		} else if (!connectionInfoSent) {
			connectionInfoSent = true;
			PacketConnectionInfo connectionInfoNew = new PacketConnectionInfo();
			connectionInfoNew.version = OCSClient.version;
			sendPacket(connectionInfoNew);
		}
	}

	public void sendPacket(Packet packet) {
		sender.sendPacket(packet);
	}

	public ArrayList<Packet> getData(int channel) {
		return receiver.getData(channel);
	}

	public String getAdress() {
		if (socket != null) { return socket.getInetAddress().getHostAddress() + ":" + socket.getPort(); }
		return "";
	}

	public void stopClient() {
		connected = false;

		receiver.stopThread();

		sender.stopThread();
	}

	/*public boolean isValid() {
		return clientInformation != null;
	}*/
}
