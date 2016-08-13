package kn.main.client;

import kn.main.common.EventType;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * task to send heartbeat packet to the server
 */
class HeartbeatTimerTask extends Thread {

	public Socket connSocket = null;

	public OutputStream out = null;
	public InputStream in = null;

	public HeartbeatTimerTask(Socket connSocket) {

		this.connSocket = connSocket;

		if(connSocket!=null) {
			try {
				this.out = connSocket.getOutputStream();
				this.in = connSocket.getInputStream();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void run () {

		if(connSocket==null)
			return;

		try {

			while(!connSocket.isClosed()) {

				String event = String.valueOf(EventType.HEARTBEAT_EVENT);
				String end_flag = "@@";
				String dtime = new Date().toString();
				byte [] heartbeat = (event + "["+dtime+"] hello server!" + end_flag).getBytes();
				byte [] response = new byte[1000];
				out.write(heartbeat);
				System.out.println("send value:"+heartbeat);
				System.out.println("send heartbeat to server...");
				//System.out.println("send heartbeat to server...and handle server's response");

				in.read(response,0,response.length);
				System.out.println("response value:"+response);
				/*
				// 4-bytes number
				in.read(response, 0, 4);
				int length = Integer.parseInt(new String(response).trim());

				String body = "";
				while(length>0) {
					System.out.println("keep reading ...");
					length -= in.read(response, 0, length);
					body += new String(response);
				}
				System.out.println("done");
				body = body.trim();
				System.out.println(body);

				// parse the body part to extract the other clients' addresses
				// into neighboursList
				if(body.contains("CLIENTSADDR:")) {
					String body_tmp = body.substring(body.indexOf("/"));
					String [] ip_tmp = body_tmp.split(";");

					Client.neighboursList.clear();
					for(String client : ip_tmp) {
						Client.neighboursList.add(client.split(":")[0].substring(1));
						//System.out.print(Client.neighboursList.get(Client.neighboursList.size()-1)+" ");
					}
					//System.out.println("");
				}
				*/

				Thread.sleep(1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}