package kn.main.server;

import kn.main.server.event_type.HandleDJEvents;
import kn.main.server.event_type.HandleHeartbeatEvent;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.server.msg_type.HeartbeatMsg;
import kn.main.server.msg_type.InteractEventMsg;
import kn.main.server.msg_type.MusicCtrlEventMsg;
import kn.main.utils.DataUtil;
import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;

import java.io.*;
import java.net.Socket;

/**
 * ServerHandleAppEventThread
 * <p>
 * this class is used to handle the client's requests, including:
 * - heartbeats
 * - dj events:
 * - dj_share_start_evt
 * - dj_share_stop_evt
 * - dj_listen_follow_evt
 * - dj_listen_unfollow_evt
 * - music ctrl event:
 * - music_switch_next_evt
 * - music_switch_previous_evt
 * - music_forward_evt
 * - music_backward_evt
 * - music_start_evt
 * - music_stop_evt
 * - music_resume_evt
 * - music_inc_volume_evt
 * - music_dec_volume_evt
 * - interact events:
 * - like_evt
 * - dislike_evt
 * - bullet_screen_evt
 * - evaluate_evt
 *
 * @author zhangjie
 * @date 2016-08-07 08:53:43 PM
 * @see kn.main.server.Server
 * @see kn.main.server.ServerCleanClientThread
 */
class ServerHandleAppEventThread extends Thread {

	public Socket connSocket = null;

	public InputStreamReader reader = null;
	public OutputStreamWriter writer = null;

	public ServerHandleAppEventThread(Socket connSocket) {

		this.connSocket = connSocket;

		if (connSocket != null) {

			try {
				InputStream in = connSocket.getInputStream();
				OutputStream out = connSocket.getOutputStream();
				reader = new InputStreamReader(in);
				writer = new OutputStreamWriter(out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * thread entry
	 */
	public void run() {

		try {
			if (connSocket == null) {
				System.out.println("Connected socket is invalid ... exit");
				connSocket.close();
				return;
			}

			while (!connSocket.isClosed()) {
				char[] buf = new char[1024];

				// step 1: read reported events from iOS\Android App
				reader.read(buf, 0, 4);
				int len = 0;
				try {
					len = Integer.parseInt(new String(buf).substring(0, 4));
					System.out.println("read "+len+" characters in total");
				} catch (Exception e) {
					System.out.println("invalid data ... kill this connection");
					connSocket.close();
					break;
				}

				String buf_s = "";
				if (len <= 1024) {
					reader.read(buf, 0, len);
					buf_s = new String(buf);
				}
				else {
					int left = len;
					while(left>=0) {
						if(left>=1024)
							left -= reader.read(buf, 0, 1024);
						else
							left -= reader.read(buf, 0, left);
						buf_s += new String(buf);
					}
				}

				// check data's validity
				if (!DataUtil.isValidData(buf_s)) {
					System.err.println("invalid data ... kill this connection");
					connSocket.close();
					break;
				}
				System.out.println("server get value: " + buf_s);

				Object obj = DataUtil.decodeAsMsg(buf_s);
				if (obj != null) {
					if (obj instanceof DJEventMsg) {
						DJEventMsg msg = (DJEventMsg) obj;
						HandleDJEvents.handle(msg);
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
				} else {
					System.out.println("invalid data ... kill this thread");
					connSocket.close();
					break;
				}

				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("this thread is killed");
	}
}