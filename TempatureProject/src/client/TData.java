package client;

public class TData {
	private double temperature;
	private double factor;
	
	// Specify start temperature and factor
	public TData(double temperature, double factor) {
		this.temperature = temperature;
		this.factor = factor;
	}
	
	// Getters/setters
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}
