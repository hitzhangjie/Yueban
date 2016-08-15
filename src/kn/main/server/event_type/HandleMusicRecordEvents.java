package kn.main.server.event_type;

import kn.main.common.EventType;
import kn.main.dao.utils.DBPool;
import kn.main.server.msg_type.DJEventMsg;
import kn.main.server.msg_type.InteractEventMsg;
import kn.main.server.msg_type.MusicEventMsg;
import kn.main.server.Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class HandleMusicRecordEvents {
    public int handle(MusicEventMsg msg) throws IOException {
        int status = 0;
        switch (msg.getEvent()) {
            case EventType.MUSIC_RECORD_CREATE_EVT:
                handleCreateRecord(msg);
                break;
            case EventType.MUSIC_RECORD_PULL_EVT:
                handlePullRecord(msg);
                break;
            case EventType.MUSIC_ALL_PULL_EVT:
                handlePullAllMusic(msg);
                break;
            default:
                status = -1;
        }
        return status;
    }

    public void handleCreateRecord (MusicEventMsg msg) throws Exception
    {
        //更新数据库
        Connection con = DBPool.getInstance().getConnection();
        String sql = "SELECT MAX(songListId) FROM yueban_songlist;";
        PreparedStatement stat = con.prepareStatement(sql);
        ResultSet rs = stat.executeQuery();
        int maxSongListId = 0;
        if(rs.next()){
            maxSongListId = rs.getInt(1);
        }
        LinkedList<Integer> songlist = msg.getSongList();
        Iterator<Integer> iter = songlist.iterator();
        if (iter != null) {
            while (iter.hasNext()) {
                sql = "INSERT INTO yueban_songlist (songListId, songId) VALUES(?, ?);";
                stat = con.prepareStatement(sql);
                stat.setInt(1, maxSongListId);
                stat.setInt(2, iter.next());
                int i = stat.executeUpdate(sql);
            }
        }
    }

    public void handlePullRecord(MusicEventMsg msg) throws Exception
    {
        Connection con = DBPool.getInstance().getConnection();
        String sql = "SELECT songId FROM yueban_songlist WHERE songListId = ?;";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1,msg.getSongListID());
        ResultSet rs = stat.executeQuery();
        while(rs.next()){

        }
        LinkedList<Integer> songlist = msg.getSongList();
        Iterator<Integer> iter = songlist.iterator();
        if (iter != null) {
            while (iter.hasNext()) {
                sql = "INSERT INTO yueban_songlist (songListId, songId) VALUES(?, ?);";
                stat = con.prepareStatement(sql);
                stat.setInt(1, maxSongListId);
                stat.setInt(2, iter.next());
                int i = stat.executeUpdate(sql);
            }
        }

    }

    public void handlePullAllMusic(MusicEventMsg msg)
    {

    }
}


