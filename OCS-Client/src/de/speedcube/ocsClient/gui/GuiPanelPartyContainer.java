package de.speedcube.ocsClient.gui;

public class GuiPanelPartyContainer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public GuiPartyGeneral generalPartyGui;
	public GuiPartyCreate createPartyGui;
	public GuiParty partyGui;

	public OCSWindow window;

	public GuiPanelPartyContainer(OCSWindow window) {
		this.window = window;

		setLayout(null);
		setBounds(500, 250, 300, 250);

		generalPartyGui = new GuiPartyGeneral(this);
		createPartyGui = new GuiPartyCreate();
		partyGui = new GuiParty(this);

		add(generalPartyGui);

		validate();
	}

	public void openPartyGui() {
		removeAll();
		add(partyGui);

		validate();
		repaint();
	}

	public void openGeneralPartyGui() {
		removeAll();
		add(generalPartyGui);

		validate();
		repaint();
	}

	public void openCreatePartyGui() {
		removeAll();
		add(createPartyGui);

		validate();
		repaint();
	}
}
