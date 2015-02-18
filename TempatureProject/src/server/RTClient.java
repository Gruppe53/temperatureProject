package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class RTClient implements Runnable {
	protected Socket client = null;
	protected String msg;
	private DataInputStream input;
	private PrintStream printer;
	
	public RTClient(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			// Input FROM client
			input = new DataInputStream(client.getInputStream());
			// Output TO client
			printer = new PrintStream(client.getOutputStream());
			
			while(true) {
				// Testing... nothing is working :<
				String str = input.readUTF();
				
				double dou = input.readDouble();
				
				System.out.println(str);
				System.out.println(dou);
				
				printer.println(dou);
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}