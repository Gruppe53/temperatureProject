package client.GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JFrame f;
	private static JComponent c;
	
	private static ClientGUI instance = null;
	
	public static ClientGUI getInstance() {
		if(instance == null)
			instance = new ClientGUI();
		
		return instance;
	}
	
	private ClientGUI() {
		// Set layout manager
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(640,524));
		
		// Add components
		// Exmaple:
		// TestObject test = new TestObject();
		// add(test);
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
}
