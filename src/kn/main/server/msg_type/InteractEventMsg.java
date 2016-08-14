package kn.main.server.msg_type;

/**
 * dj、跟随者交互事件消息
 *
 * @see kn.main.common.EventType
 */
public class InteractEventMsg {
    int event;
    int djID;
    int uID;

    public int getDjID() {
        return djID;
    }

    public void setDjID(int djID) {
        this.djID = djID;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }
}