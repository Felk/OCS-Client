package de.speedcube.ocsClient;

import java.util.ArrayList;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.*;

public abstract class PacketHandler {

	protected Client client;

	public abstract void processPacket(Packet p);

	public PacketHandler(Client client) {
		this.client = client;
	}

	public void handlePackets(int channel) {
		ArrayList<Packet> packets;
		packets = client.getData(channel);

		for (Packet p : packets) {
			processPacket(p);
		}
	}

	public abstract void reset();
}
