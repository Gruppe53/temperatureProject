package server;

import java.util.ArrayList;
import java.util.List;

public class TStoredData {
	private String description;
	private List<Double> temperature = new ArrayList<Double>();

	public TStoredData(String description) {
		this.description = description;
	}
	
	public boolean addTemperature(double temperature) {
		return this.temperature.add(temperature);
	}
	
	public double getTemperatureSum() {
		double sum = 0;
		
		for(double t : temperature)
			sum += t;
			
		return sum;
	}
	
	public List<Double> getTemperature() {
		return this.temperature;
	}

	public double getTemperature(int index) {
		return this.temperature.get(index);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

