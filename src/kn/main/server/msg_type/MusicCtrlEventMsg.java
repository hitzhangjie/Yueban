package kn.main.server.msg_type;

/**
 * 音乐控制事件消息
 *
 * @see kn.main.common.EventType
 */
public class MusicCtrlEventMsg {

	private int event;
	private int dj_uid;
	private int song_id;

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

	public int getSong_id() {
		return song_id;
	}

	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
}

