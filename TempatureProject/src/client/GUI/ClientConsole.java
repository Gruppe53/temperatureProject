package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ClientConsole extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel degsLabel;
	private JLabel tempLabel;
	
	public ClientConsole() {
		// Some default components
		Dimension small = new Dimension(90,60);
		Dimension large = new Dimension(180,60);
		Font bold = new Font("Verdana", Font.BOLD, 36);
		Font norm = new Font("Verdana", Font.PLAIN, 32);
		
		// Layout of JPanel
		setLayout(new MigLayout("insets 0"));
		
		setBackground(Color.decode("#333333"));
		
		setMinimumSize(large);
		setMaximumSize(large);
		setPreferredSize(large);
		
		// Layout of labels
		JLabel[] l = {
				degsLabel = new JLabel(),
				tempLabel = new JLabel()
		};
		
		for(JLabel j : l) {
			j.setBackground(Color.decode("#333333"));
			j.setForeground(Color.WHITE);
			
			j.setMinimumSize(small);
			j.setMaximumSize(small);
			j.setPreferredSize(small);
			
			j.setOpaque(true);
		}
		
		degsLabel.setFont(bold);
		degsLabel.setText("°C: ");
		
		tempLabel.setFont(norm);
		tempLabel.setText("21.0");
		
		add(degsLabel);
		add(tempLabel);
	}
}
