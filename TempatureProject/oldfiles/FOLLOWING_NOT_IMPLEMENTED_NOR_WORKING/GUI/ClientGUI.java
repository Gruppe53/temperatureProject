package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JFrame f;
	private static JComponent c;
	
	private ClientConsole console = new ClientConsole();
	private ClientButtons buttons = new ClientButtons();
	
	public ClientGUI() {
		// Set layout manager
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(540,68));
		
		add(console);
	}
	
	public void createAndShowGUI(String name) {
        // Create window
		f = new JFrame(name);
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBackground(Color.decode("#333333"));
		f.setResizable(false);

        // Create the content pane
        c = new ClientGUI();
        
        c.setOpaque(false);
        f.setContentPane(c);

        // Draw the window
        f.pack();
        f.setVisible(true);
    }

	public void updateTemp(String temp) {
		remove(console);
		validate();
		repaint();
	}
}
