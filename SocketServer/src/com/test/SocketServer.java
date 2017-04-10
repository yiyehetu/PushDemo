package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServer {
	BufferedWriter writer;
	BufferedReader reader;

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.start();
	}

	private void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("server start...");
			while(true){
				socket = serverSocket.accept();
				managerConnection(socket);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 管理Socket连接
	private void managerConnection(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("server accept: client" + socket.hashCode());
				try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
				}

			}
		}).start();
	}

	private void test() {
		// 开启测试任务
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					writer.write("heart beat once...\n");
					System.out.println("heart beat once...");
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 3000, 3000);
	}

}
