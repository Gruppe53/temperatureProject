package server;

import java.nio.ByteBuffer;

public class TConverter {
	public static double convertTemperature(byte[] b) {
		return ByteBuffer.wrap(b).getDouble();
	}
	
	public static char temperatureToChar(byte [] b) {
		return ByteBuffer.wrap(b).getChar();
	}
}
