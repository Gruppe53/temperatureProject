package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ClientConsole extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel degsLabel;
	private JLabel tempLabel;
	
	/**
	 * @author Kristin
	 * 
	 * Labels used for the temperature sensor.
	 * <p>
	 * Controls the temperature label which prints<br>
	 * the current temperature on screen.
	 */
	public ClientConsole() {
		// Some default components
		Dimension small = new Dimension(90,60);
		Dimension large = new Dimension(180,60);
		Font bold = new Font("Verdana", Font.BOLD, 36);
		Font norm = new Font("Verdana", Font.PLAIN, 32);
		
		// Layout of class panel
		// Note: "insets 0" in this case just makes sure no
		// padding is added to components in the layout
		this.setLayout(new MigLayout("insets 0"));
		
		this.setBackground(Color.decode("#333333"));
		
		this.setMinimumSize(large);
		this.setMaximumSize(large);
		this.setPreferredSize(large);
		
		this.setOpaque(true);
		
		// Layout of labels
		JLabel[] l = {
				this.degsLabel = new JLabel(),
				this.tempLabel = new JLabel()
		};
		
		// Setup the labels (done as a for-each as both has the same options enabled)
		for(JLabel j : l) {
			j.setBackground(Color.decode("#333333"));
			j.setForeground(Color.WHITE);
			
			j.setMinimumSize(small);
			j.setMaximumSize(small);
			j.setPreferredSize(small);
			
			j.setOpaque(true);
		}
		
		// Setup options that are indistinct between the labels
		this.degsLabel.setFont(bold);
		this.degsLabel.setText("°C: ");
		
		this.tempLabel.setFont(norm);
		this.tempLabel.setText("21.0");

		// Add labels to panel
		this.add(degsLabel);
		this.add(tempLabel);
	}
	
	/**
	 * Will update the current temperature label and repaint the panel.
	 * 
	 * @param temperature the new temperature.
	 */
	public void updateTemperature(String temperature) {
		// Remove label from component list
		this.remove(tempLabel);
		
		// Update temperature text
		this.tempLabel.setText(temperature);
		
		// Re-add the updated component
		this.add(tempLabel);
		
		// Validate the panel and repaint it
		this.validate();
		this.repaint();
	}
}
