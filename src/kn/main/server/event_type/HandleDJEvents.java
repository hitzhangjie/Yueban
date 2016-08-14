package kn.main.server.event_type;

import kn.main.server.Server;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.common.EventType;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by zhangjie on 8/13/16.
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

public class HandleDJEvents {
    public int Handle(DJEventMsg msg)
    {
        int status = 0;

        switch (msg.getEvent()) {
            case EventType.DJ_SHARE_START_EVT:
                HandleDjStart(msg);
                break;
            case EventType.DJ_SHARE_STOP_EVT:
                HandleDjStop(msg);
                break;
            case EventType.DJ_LISTEN_FOLLOW_EVT:
                HandleFollower(msg);
                break;
            case EventType.DJ_LISTEN_UNFOLLOW_EVT:
                HandleUnFollower(msg);
                break;
            default:
                status = -1;
        }
        return status;
    }

    protected void HandleDjStart(DJEventMsg event)
    {
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
            synchronized (Server.LOCK){
                sockets = Server.socketsList;
            }
            DjStartMsg djMsg = new DjStartMsg(EventType.DJ_SHARE_START_EVT,
                    "test Song Name", event.getDj_uid());
            String str = djMsg.encodeToString();

            Iterator<Socket> iter = Server.socketsList.iterator();
            while(iter.hasNext()) {
                pushMsg(iter.next(), str);
            }
        }
    }

    protected void HandleDjStop(DJEventMsg event)
    {
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
            synchronized (Server.LOCK){
                sockets = Server.socketsList;
            }
            DjStopMsg djMsg = new DjStopMsg(EventType.DJ_SHARE_STOP_EVT,
                    event.getDj_uid());
            String str = djMsg.encodeToString();

            Iterator<Socket> iter = Server.socketsList.iterator();
            while(iter.hasNext()) {
                pushMsg(iter.next(), str);
            }
        }
    }
    protected void pushMsg(Socket sock, String msg)
    {
        System.out.println("log test send msg : " + msg);
    }

    protected void HandleFollower(DJEventMsg event)
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

    protected void HandleUnFollower(DJEventMsg event)
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
