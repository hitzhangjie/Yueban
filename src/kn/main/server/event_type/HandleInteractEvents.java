package kn.main.server.event_type;

import com.sun.corba.se.spi.activation.*;
import kn.main.common.EventType;
import kn.main.utils.InteractEvents;
import kn.main.server.Server;

/**
 * Created by zhangjie on 8/13/16.
 */
public class HandleInteractEvents {

    //交互事件内部事件分发
    public int handleInteractEvents(InteractEvents interactEvents){
        int ishandled = 1;
        switch(interactEvents.getEvent()){
            case EventType.LIKE_EVT:
                handleLikeEvent();
                break;
            case EventType.DISLIKE_EVT :
                handleDisLikeEvent();
                break;
            default:
                ishandled = 0;
        }
        return ishandled;
    }

    public int handleLikeEvent(){
        synchronized(Server.LOCK_DJ){

        }
        return 0;
    }

    public  int handleDisLikeEvent(){
        return 0;
    }
}
