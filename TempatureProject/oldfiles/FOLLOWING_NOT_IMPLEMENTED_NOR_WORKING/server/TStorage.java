package server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class TStorage {

	public static boolean storeTemperature(byte[] b) {
		// TODO
		/*
		 * 1. Maybe convert bytes? 2. Save data to file. 3. Return true if
		 * successful, false otherwise
		 */
		try {
			File f = new File("materials/temperature");
			
			if (f.exists() && !f.isDirectory()) {
				writeToFile(f, TConverter.temperatureToChar(b));
				return true;
			} 
			else {
				f.createNewFile();
				writeToFile(f, TConverter.temperatureToChar(b));
				return true;
			}
			
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}
	
	private static void writeToFile(File f, char b) {
		FileWriter writer;
		try {
			writer = new FileWriter(f);
			writer.write(b + ",");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static byte[] readTemperature() {
		// TODO
		/*
		 * 1. Read file from disk 2. If not given as bytes, convert back to
		 * bytes 3. Return bytes read from disk
		 */
		String str = null;
		byte b[];
		
		try {
			FileReader fr = new FileReader("temperature.txt");
			List<Character> a = new ArrayList<Character>();

			fr.read((CharBuffer) a);
			for (char c : a) {
				System.out.println(c);
				str += c;
			}
			fr.close();
			b = str.getBytes("UTF-8");

			return b;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

}
