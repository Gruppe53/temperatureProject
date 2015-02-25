package client.GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public final class ClientGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JFrame f;
	private static JComponent c;
	
	public static ClientGUI getInstance() {
		if()
	}
	
	private ClientGUI() {
		// Set layout manager
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(540,68));
		
		add(ClientConsole.getInstance());
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
