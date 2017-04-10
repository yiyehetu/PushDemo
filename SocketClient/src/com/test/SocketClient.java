package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		client.start();
	}

	private void start() {
		// 控制台读取内容
		BufferedReader inputReader = null;
		Socket socket = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			socket = new Socket("127.0.0.1", 9999);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			startServerReplyListener(reader);
			// 输入
			String inputContent;
			while (!(inputContent = inputReader.readLine()).equals("bye")) {
				writer.write(inputContent + "\n");
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputReader != null) {
				try {
					inputReader.close();
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
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	// 接收数据监听
	private void startServerReplyListener(final BufferedReader reader){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String response;
				try {
					while((response = reader.readLine()) != null){
						System.out.println(response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}

}
