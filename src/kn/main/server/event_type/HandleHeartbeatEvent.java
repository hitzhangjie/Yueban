package kn.main.server.event_type;

import kn.main.common.EventType;
import kn.main.server.msg_type.HeartbeatMsg;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

/**
 * Created by zhangjie on 8/13/16.
 */
public class HandleHeartbeatEvent {

	public static void handleEvent(HeartbeatMsg hbMsg, Socket connSocket) throws IOException {

		OutputStreamWriter writer = new OutputStreamWriter(connSocket.getOutputStream());
		String echoMsg = hbMsg.getEchoMsg();

		//synchronized (connSocket) {
			writer.write(echoMsg);
			writer.flush();
		//}
		System.out.println("server send value:"+echoMsg);
	}
}
