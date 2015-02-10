package client;

import java.util.Random;

public class TemperatureSensor {
	RNGData d;
	
	// Default values
	private final double MAX_TEMP	= 24.00; // Maximum init temperature
	private final double MIN_TEMP	= 14.00; // Minimum init temperature
	private final double INIT_TEMP	= 21.00; // Default init temperature
	private final double MAX_FAC	=  0.10; // Maximum init factor
	private final double MIN_FAC	=  0.01; // Minimum init factor
	private final double INIT_FAC	=  0.05; // Default init factor

	public void newTemperature() {
		double newTemp;
		
		Random r = new Random();
		
		// How many percent should we increase/decrease with?
		newTemp = d.getFactor() * r.nextDouble();
		
		if(r.nextBoolean())
			// Increase
			d.setTemperature(d.getTemperature() *  (1 + newTemp));
		else
			// Decrease
			d.setTemperature(d.getTemperature() * (1 - newTemp));
	}
	
	public byte[] getTemperatureAsByte() {
		byte[] b = new byte[8];
		
		long l = Double.doubleToLongBits(d.getTemperature());
		
		for(int i = 0; i < 8; i++)
			b[i] = (byte) ((l >> ((7 - i) * 8)) & 0xff);
		
		return b;
	}
	
	// Use default data values
	public TemperatureSensor() {
		d = new RNGData(INIT_TEMP, INIT_FAC);
	}
	
	// Specify initial data values
	public TemperatureSensor(int temperature, double factor) throws IllegalArgumentException {
		if(temperature >= MIN_TEMP && temperature <= MAX_TEMP) // Check that temperature is within acceptable range
			if(factor >= MIN_FAC && factor <= MAX_FAC) // Check that factor is within acceptable range
				d = new RNGData(temperature, factor); // Create RNGData with parameter values
			else
				throw new IllegalArgumentException("Factor is not within acceptable range (" + MIN_FAC + "-" + MAX_FAC +")");
		else
			throw new IllegalArgumentException("The initial temperature value is not within acceptable range (" + MIN_TEMP + "-" + MAX_TEMP +")");
	}
}
