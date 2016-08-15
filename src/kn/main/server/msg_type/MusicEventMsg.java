package kn.main.server.msg_type;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MusicEventMsg {

    // 上报的事件类型
    private int event;
    // dj的uid
    private LinkedList<Integer> songList;

    private int req_uin;

    private  int songListID;

    public int getSongListID() {
        return songListID;
    }

    public void setSongListID(int songListID) {
        this.songListID = songListID;
    }

    public int getReq_uin() {
        return req_uin;
    }

    public void setReq_uin(int req_uin) {
        this.req_uin = req_uin;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public LinkedList<Integer> getSongList() {
        return songList;
    }

    public void setSongList(LinkedList<Integer> songList) {
        this.songList = songList;
    }
}
