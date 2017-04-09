package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.start();
	}

	private void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("server start...");
			socket = serverSocket.accept();
			System.out.println("server accept: client" + socket.hashCode());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String msg = null;
			while ((msg = reader.readLine()) != null) {
				System.out.println(msg);
				writer.write("server apply:" + msg + "\n");
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
