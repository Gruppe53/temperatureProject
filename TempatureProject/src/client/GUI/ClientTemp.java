package client.GUI;

import java.awt.Graphics;

public class ClientTemp {
	private String temp;

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public void paintTemp(Graphics g) {
		g.drawString(this.temp, 0, 0);
	}
}
