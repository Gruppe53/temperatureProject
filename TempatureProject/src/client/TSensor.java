package client;

import java.text.DecimalFormat;
import java.util.Random;

public class TSensor {
	TData d;
	
	// Default values
	private final double MAX_TEMP	= 24.00; // Maximum init temperature
	private final double MIN_TEMP	= 14.00; // Minimum init temperature
	private final double INIT_TEMP	= 21.00; // Default init temperature
	private final double MAX_FAC	=  0.10; // Maximum init factor
	private final double MIN_FAC	=  0.01; // Minimum init factor
	private final double INIT_FAC	=  0.05; // Default init factor
	
	// Class constructor with default data values
	public TSensor() {
		d = new TData(INIT_TEMP, INIT_FAC);
	}
	
	/**
	 * Class constructor that allows parameters to set the initial temperature and the factor.
	 * The temperature argument has to be within the acceptable range (14-21).
	 * The factor argument has to be within the acceptable range (0.01 - 0.1).
	 * 
	 * @param temperature	the initial temperature value
	 * @param factor		the factor which is used to determine the maximum increase or decrease of the temperature
	 * @throws IllegalArgumentException
	 */
	public TSensor(int temperature, double factor) throws IllegalArgumentException {
		// Specify initial data values
		if(temperature >= MIN_TEMP && temperature <= MAX_TEMP) // Check that temperature is within acceptable range
			if(factor >= MIN_FAC && factor <= MAX_FAC) // Check that factor is within acceptable range
				d = new TData(temperature, factor); // Create RNGData with parameter values
			else
				throw new IllegalArgumentException("Factor is not within acceptable range (" + MIN_FAC + "-" + MAX_FAC +")");
		else
			throw new IllegalArgumentException("The initial temperature value is not within acceptable range (" + MIN_TEMP + "-" + MAX_TEMP +")");
	}
	
	/**
	 * Increases or decreases the current temperature within the given factor (default 0.05).
	 * <p>
	 * When called, the method will first determine with how much it will 
	 * increase or decrease with (0.00 to 0.05 as default), this is called newTemp.
	 * Next it will decide whether to increase or decrease.
	 * <p>
	 * The formula:
	 * Current temperature * (1 (+ OR -) newTemp).
	 * <p>
	 * If the new temperature is above or below the acceptable range (14-24)
	 * it will either set the temperature to 24 or 14, respectively
	 */
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
		
		// If temperature is above/below minimum, round down/up
		if(d.getTemperature() > MAX_TEMP)
			d.setTemperature(MAX_TEMP);
		
		if(d.getTemperature() < MIN_TEMP)
			d.setTemperature(MIN_TEMP);
	}
	
	/**
	 * Converts the temperature from double to bytes.
	 * 
	 * @return the temperature as a byte-array.
	 */
	public byte[] getTemperatureAsByte() {
		// Convert double to bits
		long l = Double.doubleToLongBits(getTemperatureAsDouble(2));
		
		// Create new byte array
		byte[] b = new byte[5];
		
		// Convert bits to bytes and insert into byte array
		for(int i = 0; i < 8; i++)
			b[i] = (byte) ((l >> ((7 - i) * 8)) & 0xff);
		
		return b;
	}
	
	/**
	 * Will return the current temperature as a double. It will include all decimals.
	 * An integer argument can be given to decide the amount of decimals.
	 * 
	 * @return the current temperature
	 */
	public double getTemperatureAsDouble() {
		return d.getTemperature();
	}
	
	/**
	 * Formats the current temperature with a desired amount of decimals.
	 * <p>
	 * The format will drop any trailing zeros.
	 * E.g. digits = 2, temperature has been read to 21.90, will be returned as 21.9.
	 * 
	 * @param digits the amount of desired decimals
	 * @return the desired format of the temperature
	 */
	public double getTemperatureAsDouble(int digits) {
		String decimals = "#.";
		
		for(;digits != 0; digits--)
			decimals += "#";
		
		DecimalFormat dFormat = new DecimalFormat(decimals);
		
		return new Double(dFormat.format(d.getTemperature()));
	}
}
