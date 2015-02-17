package server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class RunningClient implements Runnable {
	protected Socket client = null;
	protected String msg;
	private DataInputStream input;
	private PrintStream printer;
	private ByteArrayOutputStream buffer;
	private byte[] line;
	
	public RunningClient(Socket client, String msg) {
		this.client = client;
		this.msg = msg;
	}

	@Override
	public void run() {
		try {
			input = new DataInputStream(client.getInputStream()); // Input FROM client
			printer = new PrintStream(client.getOutputStream()); // Output TO client
			
			while(true) {
				// Read input from client
				buffer = new ByteArrayOutputStream();
				
				// Wrap bytes
				for(int i; (i = input.read(line)) != -1;)
					buffer.write(line, 0, i);
				
				// Flush buffer
				buffer.flush();
				
				// Write bytes to byte array
				line = buffer.toByteArray();
				
				// Print input from client to screen
				System.out.println(ByteBuffer.wrap(line).getDouble());
				
				// Return received byte array to client
				printer.println(line);
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}