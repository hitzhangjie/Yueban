package kn.main.server.event_type;

import kn.main.common.EventType;
import kn.main.server.msg_type.InteractEventMsg;
import kn.main.server.Server;

import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kn.main.dao.utils.DBPool;
import java.net.Socket;

/**
 * Created by zhangjie on 8/13/16.
 */
public class HandleInteractEvents {

    //交互事件内部事件分发
    public int handleInteractEvents(InteractEventMsg interactEvents) throws Exception{
        int ishandled = 1;
        switch(interactEvents.getEvent()){
            case EventType.LIKE_EVT:
                handleLikeEvent(interactEvents);
                break;
            case EventType.DISLIKE_EVT:
                handleDisLikeEvent(interactEvents);
                break;
            case EventType.BULLET_SCREEN_START_EVT:
                handleBulletScreenStartEvt(interactEvents);
                break;
            case EventType.BULLET_SCREEN_ADD_EVT:
                handleBulletScreenAddEvt(interactEvents);
                break;
            case EventType.BULLET_SCREEN_NEXT5_EVT:
                handleBulletScreenNextsEvt(interactEvents);
                break;
            case EventType.BULLET_SCREEN_STOP_EVT:
                handleBulletScreenStopEvt(interactEvents);
                break;
            default:
                ishandled = 0;
        }
        return ishandled;
    }

    //处理送鲜花事件
    public int handleLikeEvent(InteractEventMsg interactEvents) throws Exception{
        LinkedList<Integer> djFollows = null;

        synchronized(Server.LOCK_DJ){
            djFollows = Server.djFollowersMap.get(interactEvents.getDjID());
        }

        //遍历跟随者同步送鲜花
        Socket cur_socket = null;
        for(Iterator<Integer>iter = djFollows.iterator(); iter.hasNext();){
            if(iter.next().equals(interactEvents.getuID())){
                synchronized(Server.LOCK_DJ) {
                    cur_socket = Server.uinSocketMap.get(interactEvents.getuID());
                }
                String event = String.valueOf(EventType.LIKE_EVT);
                String end_flag = "@@";
                String likeevent = event +  end_flag;
                OutputStreamWriter writer = new OutputStreamWriter(cur_socket.getOutputStream());
                writer.write(likeevent);
                writer.flush();
                writer.close();
            }
        }

        //更新数据库
        Connection con = DBPool.getInstance().getConnection();
        String sql = "SELECT userPraisedCount FROM yueban_user WHERE userId = ?";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1,interactEvents.getDjID());
        ResultSet rs = stat.executeQuery();
        int userPraisedCount = 0;
        if(rs.next()){
            userPraisedCount = rs.getInt(1);
        }
        userPraisedCount++;

        sql = "update yueban_user set userPraisedCount=? where userId=? ";
        PreparedStatement statUpdate = con.prepareStatement(sql);
        statUpdate.setInt(1,userPraisedCount);
        statUpdate.setInt(2,interactEvents.getDjID());
        int i = statUpdate.executeUpdate();
        if (i != 1){
            //失败
        }
        DBPool.closeConnection(con);
        return 0;
    }

    //处理丢拖鞋事件
    public  int handleDisLikeEvent(InteractEventMsg interactEvents) throws Exception{
        LinkedList<Integer> djFollows = null;
        synchronized(Server.LOCK_DJ){
            djFollows = Server.djFollowersMap.get(interactEvents.getDjID());
        }

        //遍历跟随者同步丢板鞋
        Socket cur_socket = null;
        for(Iterator<Integer>iter = djFollows.iterator(); iter.hasNext();){
            if(iter.next().equals(interactEvents.getuID())){
                synchronized(Server.LOCK_DJ) {
                    cur_socket = Server.uinSocketMap.get(interactEvents.getuID());
                }
                String event = String.valueOf(EventType.DISLIKE_EVT);
                String end_flag = "@@";
                String dislikeevent = event +  end_flag;
                OutputStreamWriter writer = new OutputStreamWriter(cur_socket.getOutputStream());
                writer.write(dislikeevent);
                writer.flush();
                writer.close();
            }
        }

        //更新数据库
        Connection con = DBPool.getInstance().getConnection();
        String sql = "SELECT userCriticizedCount FROM yueban_user WHERE userId = ?";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1,interactEvents.getDjID());
        ResultSet rs = stat.executeQuery();
        int userCriticizedCount = 0;
        if(rs.next()){
            userCriticizedCount = rs.getInt(1);
        }
        userCriticizedCount++;

        sql = "update yueban_user set userCriticizedCount=? where userId=? ";
        PreparedStatement statUpdate = con.prepareStatement(sql);
        statUpdate.setInt(1,userCriticizedCount);
        statUpdate.setInt(2,interactEvents.getDjID());
        int i = statUpdate.executeUpdate();
        if (i != 1){
            //失败
        }
        DBPool.closeConnection(con);
        return 0;
    }

    //预留：弹幕接口
    public int handleBulletScreenStartEvt(InteractEventMsg interactEvents){
        return 0;
    }

    public int handleBulletScreenAddEvt(InteractEventMsg interactEvents){
        return 0;
    }

    public int handleBulletScreenNextsEvt(InteractEventMsg interactEvents){
        return 0;
    }

    public int handleBulletScreenStopEvt(InteractEventMsg interactEvents){
        return 0;
    }
}
