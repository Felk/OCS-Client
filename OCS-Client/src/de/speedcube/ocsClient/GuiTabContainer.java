package de.speedcube.ocsClient;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.speedcube.ocsUtilities.packets.PacketLogout;

public class GuiTabContainer extends GuiPanel {

	public OCSClient window;
	public GuiPanelLogin loginPanel;
	public GuiPanelChatContainer chatContainer;
	public boolean tabsEnabled;

	public JTabbedPane tabbedPane;

	public GuiTabContainer(OCSClient window, GuiPanelLogin loginPanel, GuiPanelChatContainer chatContainer) {
		this.window = window;
		this.loginPanel = loginPanel;
		this.chatContainer = chatContainer;

		setLayout(null);
		setName("guiContainer");
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, window.getWidth(), window.getHeight());
		tabbedPane.addTab(SystemStrings.getString("system.tab.login"), loginPanel);

		add(tabbedPane);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					disableTabs();
				}
			}
		});
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
		window.client.sendPacket(new PacketLogout());
	}
}
