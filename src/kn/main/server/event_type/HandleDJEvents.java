package kn.main.server.event_type;


/**
 * Created by zhangjie on 8/13/16.
 */
public class HandleDJEvents {
    public int Handle(DJEventMsg msg)
    {
        int status = 0;

        switch (msg.getEvent()) {
            case EventType.DJ_SHARE_START_EVT:
                HandleDjStart(msg);
                break;
            case EventType.DJ_SHARE_STOP_EVT:
                break;
            default:
                status = -1;
        }
        return status;
    }

    protected int HandleDjStart(DJEventMsg event)
    {
        int status = 0;

        do {
            synchronized() {
                if(null == djFollowersMap)
                if (djFollowersMap.containsKey(clientFd)) {
                    //System.out.println("fd is Dj, Dj index: " + clientFd);
                    status = -1;
                }
                else{

                }
            }
        } whlie(0);

    }
}
