package de.speedcube.ocsClient.gui;

import java.text.ParseException;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;

import de.speedcube.ocsClient.SystemStrings;

public class OCSWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public GuiTabLogin loginTab;
	public GuiTabChat chatTab;

	public GuiTabContainer tabContainer;

	private void setupStyle() {

		try {
			SynthLookAndFeel laf = new SynthLookAndFeel();
			laf.load(getClass().getResourceAsStream("/laf.xml"), getClass());
			UIManager.setLookAndFeel(laf);
		} catch (ParseException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public void setupWindow(String version) {
		setTitle(SystemStrings.getString("system.title", new String[] { version }));

		setupStyle();

		setSize(820, 600);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

		GuiPanelChat chatPanel = new GuiPanelChat(this);
		GuiPanelUserlist userlistPanel = new GuiPanelUserlist();
		//GuiPanelTimer timerPanel = new GuiPanelTimer(this);
		GuiPanelPartyContainer partyPanelContainer = new GuiPanelPartyContainer(this);

		loginTab = new GuiTabLogin(this);
		chatTab = new GuiTabChat(this, chatPanel, userlistPanel, partyPanelContainer);

		tabContainer = new GuiTabContainer(this, loginTab, chatTab);

		add(tabContainer);

		validate();
		repaint();
	}

}
