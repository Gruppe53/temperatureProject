package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TemperatureStorage {
	public static boolean storeTemperature(byte[] b) {
		// TODO
		/*
		 * 1. Maybe convert bytes?
		 * 2. Save data to file.
		 * 3. Return true if successful, false otherwise
		 */
		try {
			FileOutputStream saveFile = new FileOutputStream("Temperatur.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(b);
			save.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		return false;
	}
	
	public static byte[] readTemperature() {
		byte[] b = {0};
		// TODO
		/*
		 * 1. Read file from disk
		 * 2. If not given as bytes, convert back to bytes
		 * 3. Return bytes read from disk
		 */
		FileInputStream saveFile;
		ObjectInputStream restore;
		
		try {
			saveFile = new FileInputStream("saveFile.sav");
			restore = new ObjectInputStream(saveFile);
			
			b = (byte[]) restore.readObject();
			
			restore.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return b;
	}
}