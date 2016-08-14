package kn.main.server.event_type;

import kn.main.server.Server;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.common.EventType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by zhangjie on 8/13/16.
 */

/**
 * HandleDJEvent工具类
 */
public class HandleDJEvents {

	public static int handle(DJEventMsg msg) throws IOException {
		int status = 0;

		switch (msg.getEvent()) {
			case EventType.DJ_SHARE_START_EVT:
				handleDjStartEvent(msg);
				break;
			case EventType.DJ_SHARE_STOP_EVT:
				handleDjStopEvent(msg);
				break;
			case EventType.DJ_LISTEN_FOLLOW_EVT:
				handleFollowEvent(msg);
				break;
			case EventType.DJ_LISTEN_UNFOLLOW_EVT:
				handleUnfollowEvent(msg);
				break;
			default:
				status = -1;
		}
		return status;
	}

	protected static void handleDjStartEvent(DJEventMsg event) throws IOException {
		boolean isPushNeeded = false;
		ArrayList<Socket> sockets;

		synchronized (Server.LOCK_DJ) {
			if (null == Server.djFollowersMap)
				Server.djFollowersMap = new HashMap<Integer, LinkedList<Integer>>();

			if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
				Server.djFollowersMap.put(event.getDj_uid(), new LinkedList<Integer>());
				isPushNeeded = true;
			}
		}
		if (isPushNeeded) {
			//TODO: push to all sockets, dj start
			// wrong :
			// in java, assignment a class object to another is just to pass the `reference value`,
			// so, using following code cannot avoid the race condition.
			/*
			synchronized (Server.LOCK){
                sockets = Server.socketsList;
            }
            */

			// another point, stream cannot be copied, for example, cout/cin cannot be copied in C++,
			// the rules looks very similar, we still cannot deep copy a socket, because the
			// InputStream/OutputStream is included within it.
			// so, just synchronize the operation around Server.socketsList, though the performance
			// may be poor.
			synchronized (Server.LOCK) {

				DjStartMsg djMsg = new DjStartMsg(
						EventType.DJ_SHARE_START_EVT, "test Song Name", event.getDj_uid());
				String str = djMsg.encodeToString();

				Iterator<Socket> iter = Server.socketsList.iterator();
				if (iter != null) {
					while (iter.hasNext()) {
						pushMsg(iter.next(), str);
					}
				}
			}
		}
	}

	protected static void handleDjStopEvent(DJEventMsg event) throws IOException {
		int status = 0;
		ArrayList<Socket> sockets;

		synchronized (Server.LOCK_DJ) {
			if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
				System.out.println("Dj not exists, can no stop " + event.getDj_uid());
				status = 1;
			} else {
				Server.djFollowersMap.remove(event.getDj_uid());
			}
		}
		if (status == 0) {
			//TODO:  push to all sockets, dj end
		    /*
            synchronized (Server.LOCK){
                sockets = Server.socketsList;
            }
            */
			synchronized (Server.LOCK) {
				DjStopMsg djMsg = new DjStopMsg(EventType.DJ_SHARE_STOP_EVT,
						event.getDj_uid());
				String str = djMsg.encodeToString();

				Iterator<Socket> iter = Server.socketsList.iterator();
				if (iter != null) {
					while (iter.hasNext()) {
						pushMsg(iter.next(), str);
					}
				}
			}
		}
	}

	protected static void pushMsg(Socket sock, String msg) throws IOException {

		System.out.println("log test send msg : " + msg);
		OutputStreamWriter writer = new OutputStreamWriter(sock.getOutputStream());
		writer.write(msg.toCharArray());
		writer.flush();
	}

	protected static void handleFollowEvent(DJEventMsg event) {
		int status;
		synchronized (Server.LOCK_DJ) {
			if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
				System.out.println("Dj finished  DJ_UIN:" + event.getDj_uid());
			} else {
				Server.djFollowersMap.get(event.getDj_uid()).add(event.getDj_follower_uid());
				// fixme
				// follow一个dj之后，这个事件消息应该被push到同房间中的其他用户，以便他们刷新ui，
				// 或者执行一些其他的处理，比如更新当前房间中的总人数等
				FollowDJMsg msg = new FollowDJMsg(
						event.getEvent(), event.getDj_uid(), event.getDj_follower_uid());
				String data = msg.encodeToString();
				synchronized (Server.LOCK_DJ) {
					if(Server.djFollowersMap!=null) {
						LinkedList<Integer> friendsInRoom = Server.djFollowersMap.get(event.getDj_uid());
						if(friendsInRoom!=null) {
							for(Integer followerUin : friendsInRoom) {

							}
						}
					}
				}

			}
		}
	}

	protected static void handleUnfollowEvent(DJEventMsg event) {
		int status;
		synchronized (Server.LOCK_DJ) {
			if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
				System.out.println("Dj finished  DJ_UIN:" + event.getDj_uid());
			} else {
				Server.djFollowersMap.get(event.getDj_uid()).remove(event.getDj_follower_uid());
			}
		}
	}
}

/**
 * DJ start msg
 */
class DjStartMsg {
	public DjStartMsg(int type, String songNmae, int uin) {
		this.eventType = type;
		this.songName = songNmae;
		this.djUin = uin;
	}

	public String encodeToString() {

		String split = "|";
		String endflag = "@@";

		String event = String.format("%02d", eventType);
		String payload = djUin + "" + split + songName;
		String msg = event + payload + endflag;

		int len = msg.length();
		String len_s = String.format("%04d", len);
		msg = len_s + msg;

		return msg;
	}

	private int eventType;
	private String songName;
	private int djUin;
}

/**
 * DJ stop msg
 */
class DjStopMsg {
	public DjStopMsg(int type, int uin) {
		this.eventType = type;
		this.djUin = uin;
	}

	public String encodeToString() {

		// 2 chars, standing for event field
		String event = String.format("%02d", eventType);
		String endflag = "@@";
		// delimiter '|' only exists between data items, for example, it's between djUin and djFollowerUin
		String msg = event + djUin + endflag;
		int len = msg.length();
		// 4 chars, standing for length field in protocol message
		String len_s = String.format("%04d", len);
		msg = len_s + msg;

		return msg;
	}

	private int eventType;
	private int djUin;
}

class FollowDJMsg {

	private int eventType;
	private int djUin;
	private int djFollowerUin;

	public FollowDJMsg(int eventType, int djUin, int djFollowerUin) {
		this.eventType = eventType;
		this.djUin = djUin;
		this.djFollowerUin = djFollowerUin;
	}

	public String encodeToString() {

		String event = String.format("%02d", eventType);
		String endflag = "@@";
		String delimiter = "|";

		String msg = event + djUin + delimiter + djFollowerUin + endflag;
		int len = msg.length();
		String len_s = String.format("%04d", len);
		msg = len_s + msg;

		return msg;
	}
}
