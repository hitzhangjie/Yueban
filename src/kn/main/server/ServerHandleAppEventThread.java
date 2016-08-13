package kn.main.server;

import kn.main.server.event_type.HandleHeartbeatEvent;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.server.msg_type.HeartbeatMsg;
import kn.main.server.msg_type.InteractEventMsg;
import kn.main.server.msg_type.MusicCtrlEventMsg;
import kn.main.utils.DataUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

/**
 * ServerHandleAppEventThread
 *
 * this class is used to handle the client's requests, including:
 * - heartbeats
 * - dj events:
 *   - dj_share_start_evt
 *   - dj_share_stop_evt
 *   - dj_listen_follow_evt
 *   - dj_listen_unfollow_evt
 * - music ctrl event:
 *   - music_switch_next_evt
 *   - music_switch_previous_evt
 *   - music_forward_evt
 *   - music_backward_evt
 *   - music_start_evt
 *   - music_stop_evt
 *   - music_resume_evt
 *   - music_inc_volume_evt
 *   - music_dec_volume_evt
 * - interact events:
 *   - like_evt
 *   - dislike_evt
 *   - bullet_screen_evt
 *   - evaluate_evt
 *
 * @author zhangjie
 * @see kn.main.server.Server
 * @see kn.main.server.ServerCleanClientThread
 * @date 2016-08-07 08:53:43 PM
 */
class ServerHandleAppEventThread extends Thread {

	public Socket connSocket = null;

	public InputStream in = null;
	public OutputStream out = null;

	public ServerHandleAppEventThread(Socket connSocket) {

		this.connSocket = connSocket;

		if(connSocket!=null) {

			try {
				in = connSocket.getInputStream();
				out = connSocket.getOutputStream();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * generate the clients' <address,port> info, not including current
	 * client. only one thread can enter this function to avoid read/write
	 * conflict for the shared data.
	 *
	 * @clientAddr current client's SocketAddress
	 * @return return other clients' addresses info
	 */
	public String getClientsAddrInfo(SocketAddress clientAddr) {

		String info = "";

		synchronized(Server.LOCK) {
			for(SocketAddress addr : Server.clientsList) {

				if(addr == clientAddr)
					continue;

				int index = Server.clientsList.indexOf(addr);
				Socket socket = Server.socketsList.get(index);

				if(socket.isClosed())
					continue;
				else {
					String clientInfo = ((InetSocketAddress)addr).getAddress().toString();
					int port = ((InetSocketAddress)addr).getPort();
					info += clientInfo+":"+port+";";
				}
			}
		}

		return info;
	}


	/**
	 * write the length and msg to the client
	 *
	 * @msg the message to be written to OutputStream out
	 * @out the OutputStream instance
	 */
	public void write(String msg, OutputStream out) {

		int ct = 0;

		if(out==null || msg==null || msg.length()==0)
			return;

		// only handles 1000 characters at most, bad!!!
		// fixme!!!
		int length = msg.length();
		String s = null;
		if(length<=1000)
			s = String.format("%04d", length)+new String(msg);
		else
			s = "1000"+new String(msg.substring(0,999));

		try {
			System.out.println(s);
			out.write(s.getBytes());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * thread entry
	 */
	public void run() {

		try {
			if(connSocket==null) {

				System.out.println("Socket is invalid");
				return;
			}

			while(!connSocket.isClosed()) {

				System.out.println("Thread "+Thread.currentThread().getId()+" waiting ...");

				byte [] buf = new byte[100];
				String heartbeat = "";

				// step 1: read reported events from iOS\Android App
				//while(in.read(buf)>=0)
				//while(in.read(buf)>0) {
				in.read(buf);
				String rawData = buf.toString();

				Object obj = DataUtil.decodeAsMsg(rawData);
				if(obj instanceof DJEventMsg) {
					DJEventMsg msg = (DJEventMsg)obj;
					// call your event handler function
				}
				else if(obj instanceof MusicCtrlEventMsg) {
					MusicCtrlEventMsg msg = (MusicCtrlEventMsg)obj;
					// call your event handler function
				}
				else if(obj instanceof InteractEventMsg) {
					InteractEventMsg msg = (InteractEventMsg)obj;
					// call your event handler function
				}
				else if(obj instanceof HeartbeatMsg) {
					// call following handler function
					HeartbeatMsg msg = (HeartbeatMsg)obj;
					HandleHeartbeatEvent.handleEvent(msg, connSocket);
				}
				else {
					// common events
					// call your event handler function
				}

				//heartbeat += new String(buf);
				//}

				/*
				// ack heartbeat
				String msg = "Server Side Thread "+Thread.currentThread().getId()
						+" ACK your heartbeat: \""+heartbeat+"\"";
				write(msg, out);

				// client address
				SocketAddress remoteAddress = connSocket.getRemoteSocketAddress();
				//msg = "Server Side Thread "+Thread.currentThread().getId()
				//			+" client ip:"
				//			+((InetSocketAddress)remoteAddress).getAddress().toString();
				//write(msg, out);

				// other clients' address info
				msg = getClientsAddrInfo(remoteAddress);
				msg = "Server Side Thread "+Thread.currentThread().getId()
						+" Send all clients\' info: to Client["
						+((InetSocketAddress)remoteAddress).getAddress().toString()+"]";
				write(msg, out);
				msg = "["+new Date().toString()+"] CLIENTSADDR:"+msg;
				System.out.println(msg);
				*/

				Thread.sleep(1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}