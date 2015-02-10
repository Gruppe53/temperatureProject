package client;

import java.util.Random;
import java.util.TimerTask;

public class TemperatureSensor extends TimerTask {
	RNGData d;
	
	// Default values
	private final double MAX_TEMP	= 24.00; // Maximum init temperature
	private final double MIN_TEMP	= 14.00; // Minimum init temperature
	private final double INIT_TEMP	= 21.00; // Default init temperature
	private final double MAX_FAC	=  0.10; // Maximum init factor
	private final double MIN_FAC	=  0.01; // Minimum init factor
	private final double INIT_FAC	=  0.05; // Default init factor

	@Override
	public void run() {
		// TODO sensor.run
		/*
		 * 1.	Ask "d" for initial temperature
		 * 2.	Ask "d" for factor
		 * 3a.	Multiply initial temperature with a random number between 0.00 and "d.getFactor"
		 * 		- 0.00 because the temperature might not have changed at all.
		 * 3b.	Choose whether to decrease (subtraction operand) or increase (addition operand)
		 * 		- This should be random as well
		 * 4.	Set new temperature in "d"
		 * 
		 * TODO sensor.run: How do we now return this new value to TemperatureClient? Hmmm...
		 */
		
		double newTemp;
		
		// Create new temperature
		Random r = new Random();
		
		newTemp = d.getFactor() * r.nextDouble(); // Between 0-0.05
		
		if(r.nextBoolean()) {
			System.out.print("Temperature increase with " + (newTemp) + "%, new temperature: ");
			d.setTemperature(d.getTemperature() *  (1 + newTemp));
			System.out.print(d.getTemperature() + "\n");
		}
		else {
			System.out.print("Temperature decrease with " + (newTemp) + "%, new temperature: ");
			d.setTemperature(d.getTemperature() * (1 - newTemp));
			System.out.print(d.getTemperature() + "\n");
		}
	}
	
	public byte getTemperatureAsByte() {
		byte b = 0; // b = 0xa

		// TODO sensor.getTemperature
		/*
		 * 1. Ask "d" for temperature
		 * 2. Convert returned double value to byte
		 * 3. Return new byte to calling object
		 */
		
		return b;
	}
	
	// Use default data values
	public TemperatureSensor() {
		System.out.println("Sensor init...");
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
