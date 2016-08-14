package kn.main.server;

import kn.main.server.event_type.HandleHeartbeatEvent;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.server.msg_type.HeartbeatMsg;
import kn.main.server.msg_type.InteractEventMsg;
import kn.main.server.msg_type.MusicCtrlEventMsg;
import kn.main.utils.DataUtil;

import java.io.*;
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
	 * thread entry
	 */
	public void run() {

		try {
			if(connSocket==null) {

				System.out.println("Socket is invalid");
				return;
			}

			InputStreamReader reader = new InputStreamReader(in);

			while(!connSocket.isClosed()) {

				char [] buf = new char[1000];

				// step 1: read reported events from iOS\Android App
				reader.read(buf);

				String buf_s = new String(buf);
				int index = buf_s.indexOf("@@");
				if(index<0) {
					System.out.println("invalid data ... kill this connection");
					connSocket.close();
					break;
				}

				String rawData = buf_s.substring(0 ,index+2);
				System.out.println("server get value: "+rawData);

				Object obj;
				try {
					obj = DataUtil.decodeAsMsg(rawData);
				}
				catch (Exception e) {
					e.printStackTrace();
					return;
				}

				if(obj!=null) {

					if (obj instanceof DJEventMsg) {
						DJEventMsg msg = (DJEventMsg) obj;
						// call your event handler function
					} else if (obj instanceof MusicCtrlEventMsg) {
						MusicCtrlEventMsg msg = (MusicCtrlEventMsg) obj;
						// call your event handler function
					} else if (obj instanceof InteractEventMsg) {
						InteractEventMsg msg = (InteractEventMsg) obj;
						// call your event handler function
					} else if (obj instanceof HeartbeatMsg) {
						// call following handler function
						HeartbeatMsg msg = (HeartbeatMsg) obj;
						HandleHeartbeatEvent.handleEvent(msg, connSocket);
					} else {
						// common events
						// call your event handler function
					}
				}
				else {
					connSocket.close();
					break;
				}

				Thread.sleep(1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}