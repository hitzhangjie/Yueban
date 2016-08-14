package kn.main.client;

import kn.main.common.EventType;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * task to send heartbeat packet to the server
 */
class HeartbeatTimerTask extends Thread {

	public static Socket connSocket = null;

	public OutputStream out = null;
	public InputStream in = null;

	public HeartbeatTimerTask(Socket connSocket) {

		this.connSocket = connSocket;

		if (connSocket != null) {
			try {
				this.out = connSocket.getOutputStream();
				this.in = connSocket.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void run() {

		if (connSocket == null)
			return;

		try {
			InputStreamReader reader = new InputStreamReader(in);
			while (!connSocket.isClosed()) {

				String event = String.valueOf(EventType.HEARTBEAT_EVENT);
				String end_flag = "@@";
				String dtime = new Date().toString();
				String heartbeat = event + "[" + dtime + "] hello server!" + end_flag;

				OutputStreamWriter writer = new OutputStreamWriter(out);
				writer.write(heartbeat);
				writer.flush();
				System.out.println("send value to server: " + heartbeat);

				char[] response = new char[1000];
				reader.read(response);
				System.out.println("get value from server: " + new String(response));

				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}