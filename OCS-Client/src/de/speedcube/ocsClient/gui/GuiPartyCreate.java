package de.speedcube.ocsClient.gui;

import javax.swing.*;

import de.speedcube.ocsClient.SystemStrings;
import de.speedcube.ocsUtilities.ScrambleTypes;

public class GuiPartyCreate extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public JTextField nameField;
	public JComboBox<String> typeBox;
	public JTextField roundNumField;
	public JTextField roundNumCountingField;
	public JComboBox<String> scrambleBox;

	public JButton createPartyButton;
	public JButton backPartyButton;

	public GuiPartyCreate() {
		setLayout(null);
		setBounds(0, 0, 300, 300);

		//name
		JLabel nameLabel = new JLabel(SystemStrings.getString("system.label.party.name"));
		nameLabel.setBounds(0, 20, 100, 20);
		nameField = new JTextField();
		nameField.setBounds(100, 20, 200, 20);
		nameField.setName("DefTextfield");

		//type
		JLabel typeLabel = new JLabel(SystemStrings.getString("system.label.party.type"));
		typeLabel.setBounds(0, 50, 100, 20);
		typeBox = new JComboBox<String>(new String[] { "AVG", "Best" });
		typeBox.setBounds(100, 50, 200, 20);

		//rounds
		JLabel roundNumLabel = new JLabel(SystemStrings.getString("system.label.party.roundNum"));
		roundNumLabel.setBounds(0, 80, 100, 20);
		roundNumField = new JTextField("5");
		roundNumField.setBounds(100, 80, 100, 20);
		roundNumField.setName("DefTextfield");

		/*roundNumCountingField = new JTextField("3");
		roundNumCountingField.setBounds(200, 80, 100, 20);
		roundNumCountingField.setName("DefTextfield");*/

		//scramble
		JLabel scrambleLabel = new JLabel(SystemStrings.getString("system.label.party.scramble"));
		scrambleLabel.setBounds(0, 110, 100, 20);
		scrambleBox = new JComboBox<String>(getScrambleStrings());
		scrambleBox.setBounds(100, 110, 200, 20);

		//create button
		createPartyButton = new JButton(SystemStrings.getString("system.label.party.create"));
		createPartyButton.setBounds(20, 150, 100, 30);
		createPartyButton.setName("DefButton");

		//back button
		backPartyButton = new JButton(SystemStrings.getString("system.label.back"));
		backPartyButton.setBounds(120, 150, 100, 30);
		backPartyButton.setName("DefButton");

		add(nameLabel);
		add(nameField);

		add(typeLabel);
		add(typeBox);

		add(roundNumLabel);
		add(roundNumField);
		//add(roundNumCountingField);

		add(scrambleLabel);
		add(scrambleBox);

		add(createPartyButton);
		add(backPartyButton);

		validate();
	}

	private String[] getScrambleStrings() {
		String[] types = new String[ScrambleTypes.types.size()];
		types = ScrambleTypes.types.keySet().toArray(types);
		return types;
	}
}
