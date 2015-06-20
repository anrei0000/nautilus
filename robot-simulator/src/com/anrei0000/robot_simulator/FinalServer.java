package com.anrei0000.robot_simulator;

import java.io.*;
import java.net.*;

public class FinalServer {
	private static String publickey = "0cba66066896ffb51e92bc3c36ffa627c2493770d9b0b4368a2466c801b0184e";
	private static String privatekey = "176970653848be5242059e2308dfa30245b93a13befd2ebd09f09b971273b728";
	private static byte[] nonce = new byte[24];

	
	public static void main(String[] args) throws IOException {

		System.out.println("Robot Server listening");

		ServerSocket serverSocket = new ServerSocket(6790);
		Socket socket = serverSocket.accept();

		BufferedReader in_reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter out_printer = new PrintWriter(socket.getOutputStream(), true);

		// main server loop
		while (true) {
			String cominginText = "";
			try {
				cominginText = in_reader.readLine();
				
//				System.out.println(cominginText);
				System.out.println("received from local: " + cominginText);
				System.out.println("sent to local: " + "ack");
				
				out_printer.println("ack");

			} catch (IOException e) {
				// error ("System: " + "Connection to server lost!");
				System.exit(1);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.exit(1);
				break;
			}

		}
	}
	
}