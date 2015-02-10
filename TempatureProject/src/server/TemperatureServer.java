package server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TemperatureServer {
	private ServerSocket server;
	private Socket client;
	private PrintStream printer;
	private DataInputStream input;
	private ByteArrayOutputStream buffer;
	private byte[] line = new byte[8];
	
	// Default values
	private final int SERVER_PORT = 15051;
	
	public TemperatureServer() {
		// TODO TemperatureServer.main
		/*
		 * 1. Create server with specified port
		 * 		- Port should be given through the command line
		 * 		- If no port is given through command line, use default value, SERVER_PORT
		 * 2. Create necessary I/O-objects (inputstream, printstream, clientsocket)
		 * 3. Wait for client to connect
		 * 4. Handle client
		 * 		4-1. Accept client and its inputs (inputstream should only contain bytes)
		 * 		4-2. Return client's inputs (printstream)
		 * 			- Do this until client disconnects or server program is closed 
		 * 5. Each input should be processed
		 * 		5-1. Save bytes to file (CSV or just as a byte-object)
		 * 		5-2. Calculate mean of data (convert from byte to double)
		 * 		5-3. Print mean to screen (TUI/GUI)
		 * 
		 * Extra:
		 * Try to create a GUI
		 */
		try {
			server = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("Waiting for client...");
			client = server.accept();
			System.out.println("Client connected...");
			input = new DataInputStream(client.getInputStream());
			printer = new PrintStream(client.getOutputStream());
			
			while(true) {
				System.out.println("cockbag");
				int n;
				buffer = new ByteArrayOutputStream();
				
				while((n = input.read(line, 0, line.length)) != -1) {
					buffer.write(line, 0, n);
				}
				
				line = buffer.toByteArray();
				
				System.out.println(ByteBuffer.wrap(line).getDouble());
				
				printer.println(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new TemperatureServer();
	}
}
