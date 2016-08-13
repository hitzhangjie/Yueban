package kn.main.server.msg_type;

/**
 * DJ事件消息
 *
 * @see kn.main.common.EventType
 */
public class DJEventMsg {

	// 上报的事件类型
	private int event;
	// dj的uid
	private int dj_uid;
	// dj跟随者的uid
	private int dj_follower_uid;

	// getters & setters
	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public int getDj_uid() {
		return dj_uid;
	}

	public void setDj_uid(int dj_uid) {
		this.dj_uid = dj_uid;
	}

	public int getDj_follower_uid() {
		return dj_follower_uid;
	}

	public void setDj_follower_uid(int dj_follower_uid) {
		this.dj_follower_uid = dj_follower_uid;
	}
}