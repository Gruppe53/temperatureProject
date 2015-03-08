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
	private ClientConsole con = new ClientConsole();
	
	/**
	 * @author Kristin
	 * 
	 * Class constructor for GUI. Will instantiate necessary panels<br>
	 * and holds all necessary methods to update these.
	 */
	public ClientGUI() {
		// Set layout manager
		this.setLayout(new MigLayout());
		this.setPreferredSize(new Dimension(540,68));
		
		this.setOpaque(true);
		
		this.add(con);
	}
	
	/**
	 * When GUI has been instantiated ClientGUI.createAndShowGUI<br>
	 * can be used to draw the window.
	 * 
	 * @param name name of the JFrame window
	 */
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
	
	/**
	 * Will update the temperature label.
	 * 
	 * @param temperature the new temperature
	 */
	public void updateTemperature(String temperature) {
		con.updateTemperature(temperature);
	}
}
