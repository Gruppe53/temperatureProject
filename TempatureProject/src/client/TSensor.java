package client;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public class TSensor {
	private TData d;

	// Default values
	private final double MAX_TEMP = 24.00; // Maximum init temperature
	private final double MIN_TEMP = 14.00; // Minimum init temperature
	private final double INIT_TEMP = 21.00; // Default init temperature
	private final double MAX_FAC = 0.10; // Maximum init factor
	private final double MIN_FAC = 0.01; // Minimum init factor
	private final double INIT_FAC = 0.05; // Default init factor

	/**
	 * Class constructor with default {@link TData} values
	 */
	public TSensor() {
		d = new TData(INIT_TEMP, INIT_FAC);
	}

	/**
	 * Class constructor that allows parameters to set the initial temperature and the factor.<br>
	 * The temperature argument has to be within the acceptable range (14-21).<br>
	 * The factor argument has to be within the acceptable range (0.01 - 0.1).
	 * 
	 * @param temperature the initial temperature value.
	 * @param factor
	 *            the factor which is used to determine the maximum increase or
	 *            decrease of the temperature.
	 * @throws IllegalArgumentException
	 *             is thrown if the given temperature or factor is above or below the
	 *             acceptable range.
	 */
	public TSensor(double temperature, double factor) throws IllegalArgumentException {
		// Check that temperature is within acceptable range
		if (temperature >= MIN_TEMP && temperature <= MAX_TEMP)
			// Check that factor is within acceptable range
			if (factor >= MIN_FAC && factor <= MAX_FAC)
				// Create TData with parameter values
				d = new TData(temperature, factor);
			else
				throw new IllegalArgumentException("Factor is not within acceptable range (" + MIN_FAC + "-" + MAX_FAC + ")");
		else
			throw new IllegalArgumentException("The initial temperature value is not within acceptable range (" + MIN_TEMP + "-" + MAX_TEMP + ")");
	}

	/**
	 * Increases or decreases the current temperature within the given factor (default 0.05).
	 * <p>
	 * When called, the method will first determine with how much it will<br>
	 * increase or decrease with (0.00 to 0.05 as default), this is called fac.<br>
	 * Next it will decide whether to increase or decrease.
	 * <p>
	 * The formula: Current temperature * (1 (+ OR -) fac).
	 * <p>
	 * If the new temperature is above or below the acceptable range (14-24) it<br>
	 * will either set the temperature to 24 or 14, respectively.
	 */
	public void newTemperature() {
		double curTemp = d.getTemperature();
		double fac;

		Random r = new Random();

		// How many percent should we increase/decrease with?
		fac = d.getFactor() * r.nextDouble();

		if (r.nextBoolean())
			// Increase
			curTemp *= (1 + fac);
		else
			// Decrease
			curTemp *= (1 - fac);

		// If temperature is above/below minimum set to maximum/minimum
		// temperature else calculated temperature
		if (curTemp > MAX_TEMP)
			d.setTemperature(MAX_TEMP);
		else if (curTemp < MIN_TEMP)
			d.setTemperature(MIN_TEMP);
		else
			d.setTemperature(curTemp);
	}

	/**
	 * Converts the temperature from double to bytes.
	 * 
	 * @return the temperature as a byte-array.
	 */
	public byte[] getTemperatureAsByte() {
		// Create new byte array (double = 8 bytes as JVM is platform independent)
		byte[] b = new byte[8];

		// Wrap bytes from double in b byte array
		ByteBuffer.wrap(b).putDouble(getTemperatureAsDouble(2));

		return b;
	}

	/**
	 * Will return the current temperature as a double.<br>
	 * It will include all decimals. An integer argument<br>
	 * can be given to decide the amount of decimals - {@link #getTemperatureAsDouble(int) getTemperatureAsDouble(int)}.
	 * 
	 * @return the current temperature.
	 */
	public double getTemperatureAsDouble() {
		return d.getTemperature();
	}

	/**
	 * Formats the current temperature with a given amount of decimals.
	 * <p>
	 * The format will drop any trailing zeros. E.g. digits = 2, temperature has<br>
	 * been read to 21.90, will be returned as 21.9.
	 * 
	 * @param digits the amount of desired decimals.
	 * @return the desired format of the temperature.
	 */
	public double getTemperatureAsDouble(int digits) {
		// TODO Instead of DecimalFormatSymbols.setDecimalSeparator, find a
		// solution where we force a specific locale to be used.
		// E.g.
		// NumberFormat dFormat = NumberFormat.getInstance(Locale.US)
		// dFormat = new DecimalFormat(decimals)
		// - Might work?
		
		String decimals = "#.";

		for (; digits != 0; digits--)
			decimals += "#";

		DecimalFormat dFormat = new DecimalFormat(decimals);
		DecimalFormatSymbols df = new DecimalFormatSymbols();

		// Forcing "dot" to be decimal separator, as DecimalFormat returns
		// doubles with locale settings and since Danish separator = comma = not good
		// Also, no grouping (just to be sure that no comma grouping occurs)
		df.setDecimalSeparator('.');

		dFormat.setDecimalFormatSymbols(df);
		dFormat.setGroupingUsed(false);
		
		return new Double(dFormat.format(getTemperatureAsDouble()));
	}
	
	/**
	 * Will return the current temperature as a string. The parameter digits<br>
	 * decides how many decimals the returned temperature should hold.<br>
	 * This method uses the {@link #getTemperatureAsDouble(int) getTemperatureAsDouble} method to format the output.
	 * 
	 * @param digits the amount of desired decimals.
	 * @return
	 */
	public String getTemperatureAsString(int digits) {
		return String.valueOf(getTemperatureAsDouble(digits));
	}
}
