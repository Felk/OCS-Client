package de.speedcube.ocsClient.gui;

import javax.swing.JTabbedPane;

import de.speedcube.ocsClient.SystemStrings;

public class GuiTabContainer extends GuiPanel {

	private static final long serialVersionUID = 1L;

	public OCSWindow window;
	public GuiTabLogin loginPanel;
	public GuiTabChat chatContainer;
	public boolean tabsEnabled;

	public JTabbedPane tabbedPane;

	public GuiTabContainer(OCSWindow window, GuiTabLogin loginPanel, GuiTabChat chatContainer) {
		this.window = window;
		this.loginPanel = loginPanel;
		this.chatContainer = chatContainer;

		setLayout(null);

		tabbedPane = new JTabbedPane();
		tabbedPane.setName("guiContainer");
		tabbedPane.setBounds(0, 0, window.getWidth(), window.getHeight());
		tabbedPane.addTab(SystemStrings.getString("system.tab.login"), loginPanel);

		add(tabbedPane);
	}

	public void enableTabs() {
		if (tabsEnabled) return;
		tabbedPane.setTitleAt(0, SystemStrings.getString("system.tab.logout"));
		tabbedPane.addTab(SystemStrings.getString("system.tab.chat"), chatContainer);
		tabbedPane.setSelectedComponent(chatContainer);
		tabsEnabled = true;
	}

	public void disableTabs() {
		tabbedPane.setTitleAt(0, SystemStrings.getString("system.tab.login"));
		tabbedPane.remove(chatContainer);
		tabsEnabled = false;
	}

}
