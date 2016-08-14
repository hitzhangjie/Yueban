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
        int status = 0;
        ArrayList<Socket> sockets;

        synchronized(Server.LOCK_DJ) {
            if(null == Server.djFollowersMap)
                Server.djFollowersMap = new HashMap<Integer, LinkedList<Integer>>();

            if (Server.djFollowersMap.containsKey(event.getDj_uid())) {
                System.out.println("fd is Dj, Dj uin: " + event.getDj_uid());
                status = 1;
            }
            else{
                Server.djFollowersMap.put(event.getDj_uid(), new LinkedList<Integer>());
            }
        }
        if(status == 0)
        {
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

		        DjStartMsg djMsg = new DjStartMsg(EventType.DJ_SHARE_START_EVT,
				        "test Song Name", event.getDj_uid());
		        String str = djMsg.encodeToString();

		        Iterator<Socket> iter = Server.socketsList.iterator();
		        if(iter!=null) {
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

        synchronized(Server.LOCK_DJ) {
            if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
                System.out.println("Dj not exists, can no stop " + event.getDj_uid());
                status = 1;
            }
            else{
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
		        if(iter!=null) {
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

    protected static void handleFollowEvent(DJEventMsg event)
    {
        int status;
        synchronized(Server.LOCK_DJ) {
            if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
                System.out.println("Dj finished  DJ_UIN:" + event.getDj_uid());
            }
            else{
                Server.djFollowersMap.get(event.getDj_uid()).add(event.getDj_follower_uid());
            }
        }
    }

    protected static void handleUnfollowEvent(DJEventMsg event)
    {
        int status;
        synchronized(Server.LOCK_DJ) {
            if (!Server.djFollowersMap.containsKey(event.getDj_uid())) {
                System.out.println("Dj finished  DJ_UIN:" + event.getDj_uid());
            }
            else{
                Server.djFollowersMap.get(event.getDj_uid()).remove(event.getDj_follower_uid());
            }
        }
    }
}

/**
 * DJ start msg
 */
class DjStartMsg
{
    public DjStartMsg(int type, String songNmae, int uin)
    {
        this.EventType = type;
        this.SongName = songNmae;
        this.DjUin = uin;
    }

    public String encodeToString()
    {
        String endflag = "@@";
        String split = "|";
        Integer len = 0;
        String msg = split + String.valueOf(EventType) + split + String.valueOf(DjUin) +
                 split +SongName + endflag;
        len = msg.length();
        return String.valueOf(len) + msg;
    }

    private int EventType;
    private String SongName;
    private int DjUin;
}

/**
 * DJ stop msg
 */
class DjStopMsg
{
    public DjStopMsg(int type, int uin)
    {
        this.EventType = type;
        this.DjUin = uin;
    }

    public String encodeToString()
    {
        String endflag = "@@";
        String split = "|";
        Integer len = 0;
        String msg = split + String.valueOf(EventType) + split + String.valueOf(DjUin) + endflag;
        len = msg.length();
        return String.valueOf(len) + msg;
    }

    private int EventType;
    private int DjUin;
}


