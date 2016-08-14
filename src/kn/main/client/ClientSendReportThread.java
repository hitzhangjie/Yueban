package kn.main.client;

import kn.main.client.ClientRecvNotifyThread;
import kn.main.common.EventType;

import java.io.*;
import java.net.Socket;

/**
 * ClientSendReportThread
 * <p>
 * 接收用户提交的待上报的事件，并将其上报给Server
 *
 * @author zhangjie
 * @date 2016-08-07 11:03:11 PM
 * @see ClientRecvNotifyThread
 */
class ClientSendReportThread extends Thread {

	// private EventQueueToSend;

	private Socket connSocket = null;
	private InputStreamReader reader = null;
	private OutputStreamWriter writer = null;

	public ClientSendReportThread(Socket connSocket) throws IOException {
		this.connSocket = connSocket;
		if (connSocket != null && connSocket.isConnected()) {
			InputStream in = connSocket.getInputStream();
			OutputStream out = connSocket.getOutputStream();
			reader = new InputStreamReader(in);
			writer = new OutputStreamWriter(out);
		}
	}

	public void run() {

		try {

			boolean isInDJStartMode = false;

			while (true) {

				if (isInDJStartMode) {
					String event = String.format("%02d", EventType.DJ_SHARE_START_EVT);
					String payload = "1194606858";
					String endflag = "@@";
					String data = event + payload + endflag;
					int len = data.length();
					String len_s = String.format("%04d", len);
					data = len_s + data;
					System.out.println("report:" + data);
					synchronized (Client.connSocket) {
						writer.write(data.toCharArray());
						writer.flush();
					}
					isInDJStartMode = true;
				} else {
					String event = String.format("%02d", EventType.DJ_SHARE_STOP_EVT);
					String payload = "1194606858";
					String endflag = "@@";
					String data = event + payload + endflag;
					int len = data.length();
					String len_s = String.format("%04d", len);
					data = len_s + data;
					System.out.println("report:" + data);
					synchronized (Client.connSocket) {
						writer.write(data.toCharArray());
						writer.flush();
					}
					isInDJStartMode = false;
				}

				Thread.currentThread().sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addEvent() {
	}

	private void sendEvent() {
	}
}