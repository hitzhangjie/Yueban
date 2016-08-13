package kn.main.server.event_type;

import kn.main.common.EventType;
import kn.main.server.msg_type.HeartbeatMsg;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

/**
 * Created by zhangjie on 8/13/16.
 */
public class HandleHeartbeatEvent {

	public static void handleEvent(HeartbeatMsg hbMsg, Socket connSocket) throws IOException {

		OutputStream out = connSocket.getOutputStream();
		String echoMsg = hbMsg.getEchoMsg();

		out.write(echoMsg.getBytes());
		System.out.println(echoMsg);
	}
}
