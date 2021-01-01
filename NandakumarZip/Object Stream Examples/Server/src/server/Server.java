package server;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import shared.Message;

public class Server {

	public static void main(String[] args) {
		int port = 1337;
		try (
			ServerSocket serverSocket = new ServerSocket(port);
		) {
			System.out.println("Server started at IP: " + serverSocket.getInetAddress());
			try (
				Socket clientSocket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			) {
				while (true) {
					try {
						Object o = ois.readObject();
						Message m = (Message)o;
						System.out.println("Received message: " + m);
					} catch (EOFException eof) {
						// Stream closed.
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
