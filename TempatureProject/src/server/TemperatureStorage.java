package server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class TemperatureStorage {

	public static boolean storeTemperature(byte[] b) {
		// TODO
		/*
		 * 1. Maybe convert bytes? 2. Save data to file. 3. Return true if
		 * successful, false otherwise
		 */
		TemperatureConverter.temperatureToChar(b);
		try {
			File file = new File("Temperature.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(b + ",");
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			e.getMessage();
		}

		return false;
	}

	public static List<Character> readTemperature() {
		// TODO
		/*
		 * 1. Read file from disk 2. If not given as bytes, convert back to
		 * bytes 3. Return bytes read from disk
		 */
		try {
			FileReader fr = new FileReader("Temperature.txt");
			List<Character> a = new ArrayList<Character>();

			fr.read((CharBuffer) a);
			for (char c : a) {
				System.out.println(c);
			}
			fr.close();
			return a;
		} catch (IOException e) {
			e.getMessage();
		}
		return null;
	}

}
