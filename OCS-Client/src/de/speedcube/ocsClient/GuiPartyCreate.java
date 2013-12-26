package de.speedcube.ocsClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.speedcube.ocsClient.network.Client;
import de.speedcube.ocsUtilities.packets.PacketPartyCreate;

public class GuiPartyCreate extends GuiPanel {

	private static final long serialVersionUID = 1L;

	private GuiPanelPartyContainer guiPartyContainer;
	private Client client;

	private JTextField nameField;
	private JComboBox<String> typeBox;
	private JTextField roundNumField;
	private JTextField roundNumCountingField;

	private JButton createPartyButton;
	private JButton backPartyButton;

	public GuiPartyCreate(Client client, GuiPanelPartyContainer guiPartyContainer) {
		this.guiPartyContainer = guiPartyContainer;
		this.client = client;

		setLayout(null);
		setBounds(0, 0, 300, 300);

		//name
		JLabel nameLabel = new JLabel(SystemStrings.getString("system.label.party.name"));
		nameLabel.setBounds(0, 20, 100, 20);
		nameField = new JTextField();
		nameField.setBounds(100, 20, 100, 20);
		nameField.setName("DefTextfield");

		//type
		JLabel typeLabel = new JLabel(SystemStrings.getString("system.label.party.type"));
		typeLabel.setBounds(0, 50, 100, 20);
		typeBox = new JComboBox<String>(new String[] { "AVG", "Best" });
		typeBox.setBounds(100, 50, 100, 20);

		//rounds
		JLabel roundNumLabel = new JLabel(SystemStrings.getString("system.label.party.roundNum"));
		roundNumLabel.setBounds(0, 80, 100, 20);
		roundNumField = new JTextField("5");
		roundNumField.setBounds(100, 80, 50, 20);
		roundNumField.setName("DefTextfield");

		roundNumCountingField = new JTextField("3");
		roundNumCountingField.setBounds(150, 80, 50, 20);
		roundNumCountingField.setName("DefTextfield");

		//create button
		createPartyButton = new JButton(SystemStrings.getString("system.label.party.create"));
		createPartyButton.setBounds(20, 120, 100, 30);
		createPartyButton.setName("DefButton");
		createPartyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (createParty()) {
					openGeneralPartyGui();
				}
			}
		});

		//back button
		backPartyButton = new JButton(SystemStrings.getString("system.label.back"));
		backPartyButton.setBounds(120, 120, 100, 30);
		backPartyButton.setName("DefButton");
		backPartyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				openGeneralPartyGui();
			}
		});

		add(nameLabel);
		add(nameField);

		add(typeLabel);
		add(typeBox);

		add(roundNumLabel);
		add(roundNumField);
		add(roundNumCountingField);

		add(createPartyButton);
		add(backPartyButton);

		validate();
	}

	public boolean createParty() {
		PacketPartyCreate ppc = new PacketPartyCreate();
		ppc.name = nameField.getText();
		try {
			ppc.rounds = Integer.parseInt(roundNumField.getText());
			ppc.rounds_counting = Integer.parseInt(roundNumCountingField.getText());
		} catch (NumberFormatException e) {
			roundNumCountingField.setText("");
			roundNumField.setText("");
			return false;
		}
		ppc.type = typeBox.getSelectedIndex() == 0 ? (byte) 1 : (byte) 2;
		ppc.scrambleType = "3x3";
		client.sendPacket(ppc);

		return true;
	}

	public void openGeneralPartyGui() {
		guiPartyContainer.remove(this);
		guiPartyContainer.add(guiPartyContainer.generalPartyGui);

		guiPartyContainer.validate();
		guiPartyContainer.repaint();

	}
}
