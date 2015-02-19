package client;

public class TData {
	private double temperature;
	private double factor;
	
	/**
	 * Class constructor for TData which holds the current temperature and a factor.
	 * The factor is used to determine the maximum variation in temperature (from 0.00 up to factor).
	 * 
	 * @param temperature is the initial temperature in the room
	 * @param factor maximum variation in temperature
	 */
	public TData(double temperature, double factor) {
		this.temperature = temperature;
		this.factor = factor;
	}
	
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
