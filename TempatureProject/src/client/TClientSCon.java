package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TClientSCon {
	private TSensor sensor;
	private Socket client;
	private BufferedReader input;
	private DataOutputStream output;

	// Default values
	private final String SERVER_HOST			= "localhost";
	private final int SERVER_PORT				= 17056; // Which port should we connect through
	private final TimeUnit UPDATE_UNIT			= TimeUnit.MILLISECONDS; // What time unit should we use
	private final int UPDATE_INTERVAL			= 5000; // With which interval should we update the temperature
	private final int START_TIME				= 5000; // Delay the client from sending data to the server (cosmetic reasons only)
	private final String LOCATION_DESCRIPTION	= "Test room 1"; // This should be determined by CLI
}
