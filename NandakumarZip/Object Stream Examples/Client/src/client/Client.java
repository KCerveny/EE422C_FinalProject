package client;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import shared.Message;

public class Client {

	public static void main(String[] args) {
		int port = 1337;
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter server IP: ");
		String ip = input.nextLine();
		try (
			Socket clientSocket = new Socket(ip, port);
			ObjectOutputStream ois = new ObjectOutputStream(clientSocket.getOutputStream());
		) {
			ois.flush();
			ois.writeObject(new Message("Hello, world!", 42));
			ois.writeObject(new Message("Hello, again!", 8675309));
			ois.writeObject(new Message("About to send a repeat...", 42));
			ois.writeObject(new Message("Hello, again!", 8675309));
		} catch (IOException e) {
			e.printStackTrace();
		}
		input.close();
	}
}
